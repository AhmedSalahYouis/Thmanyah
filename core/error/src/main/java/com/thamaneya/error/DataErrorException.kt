package com.thamaneya.error

class DataErrorException(val error: DataError, cause: Throwable? = null) : Exception(cause)
