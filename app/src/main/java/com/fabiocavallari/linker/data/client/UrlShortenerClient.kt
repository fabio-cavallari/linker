package com.fabiocavallari.linker.data.client

import com.fabiocavallari.linker.data.model.AliasDto
import com.fabiocavallari.linker.data.model.CreateAliasRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UrlShortenerClient {
    @POST
    suspend fun createAlias(
        @Body body: CreateAliasRequestDto
    ): Response<AliasDto>
}