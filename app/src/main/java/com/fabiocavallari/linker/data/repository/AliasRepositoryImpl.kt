package com.fabiocavallari.linker.data.repository

import com.fabiocavallari.linker.data.model.Result
import com.fabiocavallari.linker.data.remoteprovider.UrlShortenerRemoteProvider
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.domain.model.Resource
import com.fabiocavallari.linker.domain.util.asDomainModel

class AliasRepositoryImpl(
    val remoteProvider: UrlShortenerRemoteProvider
) : AliasRepository {
    override suspend fun createAlias(url: String): Resource<Alias> {
        return try {
            val response = remoteProvider.createAlias(url)
            when (response) {
                is Result.Success -> {
                    val alias = response.data.asDomainModel()
                    Resource.Success(alias)
                }
                is Result.Error ->
                    Resource.Error(response.error)
            }

        } catch(e: Exception) {
            Resource.Error(message = e.message)
        }
    }

}