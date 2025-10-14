package com.fabiocavallari.linker.domain.model

import com.fabiocavallari.linker.data.model.RootError

sealed class Resource<T>() {
    data class Success<T>(val data: T): Resource<T>()
    data class Error<T>(val error: RootError, val message: String? = null): Resource<T>()
}