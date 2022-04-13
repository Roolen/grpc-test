package com.example.grpctest.utils

import com.squareup.wire.GrpcException
import com.squareup.wire.GrpcStatus
import java.io.IOException

fun IOException.toRpcException(): GrpcException {
    return GrpcException(
        GrpcStatus.get(
            Regex("grpc-status=[0-9]*")
                .find(this.message.toString())
                ?.value?.split("=")
                ?.get(1)?.toInt() ?: 0
        ),
        Regex("grpc-message=[a-zA-Z\\s]*")
            .find(this.message.toString())?.value?.split("=")?.get(1)
    )
}