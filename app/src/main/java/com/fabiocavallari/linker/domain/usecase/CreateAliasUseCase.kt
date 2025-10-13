package com.fabiocavallari.linker.domain.usecase

import com.fabiocavallari.linker.domain.model.Link
import com.fabiocavallari.linker.domain.model.sampleLink

class CreateAliasUseCase {
    suspend fun createAlias(text: String): Result<Link> {
        return Result.success(sampleLink)
    }
}