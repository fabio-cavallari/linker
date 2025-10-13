package com.fabiocavallari.linker.domain.model

import com.fabiocavallari.linker.data.model.RootError

sealed class Resource<T>(val resourceData: T? = null, val message: String? = null, val error: RootError? = null) {
    data class Success<T>(val data: T): Resource<T>(data)
    class Error<T>(error: RootError? = null, message: String? = null, data: T? = null): Resource<T>(data, message, error)
}