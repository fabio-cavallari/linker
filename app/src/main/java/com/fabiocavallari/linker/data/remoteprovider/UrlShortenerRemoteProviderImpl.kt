package com.fabiocavallari.linker.data.remoteprovider

import com.fabiocavallari.linker.data.client.UrlShortenerClient
import com.fabiocavallari.linker.data.model.AliasDto
import com.fabiocavallari.linker.data.model.CreateAliasRequestDto
import com.fabiocavallari.linker.data.model.DataError
import com.fabiocavallari.linker.data.model.Result
import com.fabiocavallari.linker.data.util.safeApiCall

class UrlShortenerRemoteProviderImpl(
    val client: UrlShortenerClient
) : UrlShortenerRemoteProvider {
    override suspend fun createAlias(url: String): Result<AliasDto, DataError.Network> {
        return safeApiCall {
            val requestDto = CreateAliasRequestDto(url)
            client.createAlias(requestDto)
        }
    }
}