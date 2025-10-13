package com.fabiocavallari.linker.data.repository

import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.domain.model.Resource

interface AliasRepository {
    suspend fun createAlias(url: String): Resource<Alias>
}