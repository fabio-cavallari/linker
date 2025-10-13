package com.fabiocavallari.linker.data.model

import com.google.gson.annotations.SerializedName

data class CreateAliasRequestDto(
    @SerializedName("url") val url: String
)