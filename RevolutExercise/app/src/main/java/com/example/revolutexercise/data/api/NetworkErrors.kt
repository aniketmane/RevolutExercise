package com.example.revolutexercise.data.api

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrors(error: Throwable) {

    var errorMessage = "Something went wrong"

    init {
        when (error) {
            is HttpException -> {
                val errorString = error.response()?.errorBody()?.string() ?: ""
                this.errorMessage =
                    NetworkBuilder.moshi.adapter(ServerError::class.java)
                        .fromJson(errorString)?.error
                        ?: this.errorMessage
            }
            is UnknownHostException -> {
                this.errorMessage = "Server connection error."
            }
            is SocketTimeoutException -> {
                this.errorMessage = "Request timed out."
            }
            else -> {
                this.errorMessage = error.message ?: this.errorMessage
            }
        }
    }
}

data class ServerError(val error: String)