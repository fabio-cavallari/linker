package com.fabiocavallari.linker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Alias(
    val alias: String,
    val short: String,
    val original: String,
)
