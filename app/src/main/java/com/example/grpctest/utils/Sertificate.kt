package com.example.grpctest.utils

import com.example.grpctest.App
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.*

private fun socketFactoryAndTrustManager(): Pair<SSLSocketFactory, X509TrustManager> {
    val trustManager: X509TrustManager
    val sslSocketFactory: SSLSocketFactory
    try {
        trustManager = trustManagerForCertificates(
            trustedCertificatesInputStream()
        )
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(trustManager), null)
        sslSocketFactory = sslContext.socketFactory
    } catch (e: GeneralSecurityException) {
        throw RuntimeException(e)
    }

    return sslSocketFactory to trustManager
}

private fun trustedCertificatesInputStream(): InputStream {
    return App.getInstance().assets.open("cert.cer")
}

@Throws(GeneralSecurityException::class)
private fun trustManagerForCertificates(inputStream: InputStream): X509TrustManager {
    val certificateFactory = CertificateFactory.getInstance("X.509")
    val certificates = certificateFactory.generateCertificates(inputStream)
    if (certificates.isEmpty()) {
        throw IllegalArgumentException("expected non-empty set of trusted certificates")
    }

    // Put the certificates a key store.
    val password = "password".toCharArray() // Any password will work.
    val keyStore = newEmptyKeyStore(password)
    for ((index, certificate) in certificates.withIndex()) {
        val certificateAlias = index.toString()
        keyStore.setCertificateEntry(certificateAlias, certificate)
    }

    // Use it to build an X509 trust manager.
    val keyManagerFactory = KeyManagerFactory.getInstance(
        KeyManagerFactory.getDefaultAlgorithm()
    )
    keyManagerFactory.init(keyStore, password)
    val trustManagerFactory = TrustManagerFactory.getInstance(
        TrustManagerFactory.getDefaultAlgorithm()
    )
    trustManagerFactory.init(keyStore)
    val trustManagers = trustManagerFactory.trustManagers
    if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
        throw IllegalStateException(
            "Unexpected default trust managers:" + Arrays.toString(trustManagers)
        )
    }
    return trustManagers[0] as X509TrustManager
}

@Throws(GeneralSecurityException::class)
private fun newEmptyKeyStore(password: CharArray): KeyStore {
    try {
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        val inputStream: InputStream? = null // By convention, 'null' creates an empty key store.
        keyStore.load(inputStream, password)
        return keyStore
    } catch (e: IOException) {
        throw AssertionError(e)
    }
}