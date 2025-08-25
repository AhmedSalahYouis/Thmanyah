package com.thamaneya.error

interface IDataErrorProvider {
    fun fromThrowable(throwable: Throwable): DataError.Network
}