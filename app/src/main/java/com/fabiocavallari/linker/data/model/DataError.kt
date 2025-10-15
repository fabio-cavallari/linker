package com.fabiocavallari.linker.data.model

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        BAD_REQUEST,
        NOT_FOUND,
        INTERNAL_SERVER_ERROR,
        EMPTY_BODY,
        UNKNOWN,
        NO_CONNECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
    }

    enum class Local : DataError {
        INVALID_URL
    }
}