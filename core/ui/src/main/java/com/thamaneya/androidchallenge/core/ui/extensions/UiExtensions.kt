package com.thamaneya.androidchallenge.core.ui.extensions

import com.thamaneya.androidchallenge.core.ui.UiText
import com.thamaneya.androidchallenge.core.ui.UiText.*
import com.thamaneya.error.DataError
import com.thamaneya.error.R

fun DataError.toUiText(): UiText {

    return when (this) {
        DataError.Network.NETWORK_EXCEPTION -> StringResource(R.string.error_network_exception)
        DataError.Network.NETWORK_UNAVAILABLE -> StringResource(R.string.error_network_unavailable)
        DataError.Network.REQUEST_TIMEOUT -> StringResource(R.string.error_timeout)
        DataError.Network.HTTP_ERROR -> StringResource(R.string.error_http)
        DataError.Network.PARSING_ERROR -> StringResource(R.string.error_parse)
        DataError.Network.UNEXPECTED_ERROR -> StringResource(R.string.unexpected_error)
        DataError.Network.NOT_FOUND -> StringResource(R.string.not_found)
    }

}