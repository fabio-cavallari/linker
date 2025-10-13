package com.fabiocavallari.linker.domain.model

data class Link(
    val alias: String,
    val short: String,
    val original: String,
)

val sampleLink = Link(
    alias = "alias",
    short = "short",
    original = "original"
)