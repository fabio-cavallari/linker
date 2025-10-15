package com.fabiocavallari.linker.domain.model

sealed interface AppError {
    enum class Data : AppError {
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

    enum class Domain : AppError {
        INVALID_URL
    }
}