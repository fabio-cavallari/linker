package com.fabiocavallari.linker.domain.usecase

import com.fabiocavallari.linker.domain.model.AppError
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.domain.model.Resource
import com.fabiocavallari.linker.domain.repository.AliasRepository
import com.fabiocavallari.linker.domain.util.UrlValidator

class CreateAliasUseCase(
    private val repository: AliasRepository,
    private val urlValidator: UrlValidator
) {
    suspend operator fun invoke(text: String): Resource<Alias> {
        if (!urlValidator.isValid(text)) {
            return Resource.Error(AppError.Domain.INVALID_URL)
        }
        return repository.createAlias(text)
    }
}