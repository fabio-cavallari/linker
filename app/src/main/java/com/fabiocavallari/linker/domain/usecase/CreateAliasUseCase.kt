package com.fabiocavallari.linker.domain.usecase

import com.fabiocavallari.linker.data.repository.AliasRepository
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.domain.model.Resource

class CreateAliasUseCase(
    val repository: AliasRepository
) {
    suspend fun createAlias(text: String): Resource<Alias> {
        return repository.createAlias(text)
    }
}