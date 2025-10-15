package com.fabiocavallari.linker.shared

import com.fabiocavallari.linker.data.model.AliasDto
import com.fabiocavallari.linker.data.model.LinkDto
import com.fabiocavallari.linker.domain.model.Alias

val sampleUrl = "http://sample-url.com"

val sampleInvalidUrl = "sampleInvalidUrl"

val sampleAliasDto = AliasDto(
    link = LinkDto(
        self = "self",
        short = "short",
    ),
    alias = "alias",
)

val sampleAlias = Alias(
    alias = "alias",
    short = "short",
    original = "self"
)