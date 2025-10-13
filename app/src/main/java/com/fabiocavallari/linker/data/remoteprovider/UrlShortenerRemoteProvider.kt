package com.fabiocavallari.linker.data.remoteprovider

import com.fabiocavallari.linker.data.model.AliasDto
import com.fabiocavallari.linker.data.model.DataError
import com.fabiocavallari.linker.data.model.Result

interface UrlShortenerRemoteProvider {
    suspend fun createAlias(url: String): Result<AliasDto, DataError.Network>
}