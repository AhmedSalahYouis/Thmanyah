package com.thamaneya.error

import android.net.http.HttpException
import android.net.http.NetworkException
import android.os.Build
import androidx.annotation.RequiresExtension

private const val TIMEOUT = "timeout"

private const val NO_NETWORK = "Unable to resolve host"

class DataErrorProvider : IDataErrorProvider {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun fromThrowable(throwable: Throwable): DataError.Network {
        return when {
            throwable.message?.contains(NO_NETWORK) == true -> {
                DataError.Network.NETWORK_UNAVAILABLE
            }

            throwable.message?.contains(TIMEOUT, ignoreCase = true) == true -> {
                DataError.Network.REQUEST_TIMEOUT
            }

            throwable is HttpException -> {
                DataError.Network.HTTP_ERROR
            }
            
            throwable is MapperException -> {
                DataError.Network.PARSING_ERROR
            }

            throwable is NetworkException -> {
                DataError.Network.NETWORK_EXCEPTION
            }

            else -> {
                DataError.Network.UNEXPECTED_ERROR
            }
        }
    }
}