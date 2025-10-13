package com.fabiocavallari.linker.data.model

import com.google.gson.annotations.SerializedName

data class AliasDto(
    @SerializedName("_links") val link: LinkDto,
    @SerializedName("alias") val alias: String,
)