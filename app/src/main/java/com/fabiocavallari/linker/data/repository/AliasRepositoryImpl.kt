package com.fabiocavallari.linker.data.repository

import com.fabiocavallari.linker.data.model.DataError
import com.fabiocavallari.linker.data.model.Result
import com.fabiocavallari.linker.data.remoteprovider.UrlShortenerRemoteProvider
import com.fabiocavallari.linker.data.util.isValidUrl
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.domain.model.Resource
import com.fabiocavallari.linker.domain.util.asDomainModel

class AliasRepositoryImpl(
    val remoteProvider: UrlShortenerRemoteProvider
) : AliasRepository {
    override suspend fun createAlias(url: String): Resource<Alias> {
        return try {
            if (!isValidUrl(url)) {
                return Resource.Error<Alias>(DataError.Local.INVALID_URL)
            }
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
            Resource.Error(error = DataError.Network.UNKNOWN, message = e.message)
        }
    }
}