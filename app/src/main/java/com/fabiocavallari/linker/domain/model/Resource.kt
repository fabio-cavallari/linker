package com.fabiocavallari.linker.domain.model

sealed class Resource<T>() {
    data class Success<T>(val data: T): Resource<T>()
    data class Error<T>(val error: AppError, val message: String? = null): Resource<T>()
}