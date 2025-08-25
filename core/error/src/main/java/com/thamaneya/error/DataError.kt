package com.thamaneya.error

sealed interface DataError: Error {

    enum class Network : DataError {
        NOT_FOUND,
        NETWORK_EXCEPTION,
        NETWORK_UNAVAILABLE,
        REQUEST_TIMEOUT,
        HTTP_ERROR,
        PARSING_ERROR,
        UNEXPECTED_ERROR

    }
}
