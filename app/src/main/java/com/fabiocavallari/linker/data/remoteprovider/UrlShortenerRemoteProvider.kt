package com.fabiocavallari.linker.data.remoteprovider

import com.fabiocavallari.linker.data.model.AliasDto
import com.fabiocavallari.linker.domain.model.AppError
import com.fabiocavallari.linker.domain.model.Result

interface UrlShortenerRemoteProvider {
    suspend fun createAlias(url: String): Result<AliasDto, AppError.Data>
}