package com.fabiocavallari.linker.domain.util

import com.fabiocavallari.linker.data.model.AliasDto
import com.fabiocavallari.linker.domain.model.Alias

fun AliasDto.asDomainModel() = Alias(
    alias = alias,
    short = link.short,
    original = link.self,
)