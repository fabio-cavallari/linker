package com.fabiocavallari.linker.data.model

import com.google.gson.annotations.SerializedName

data class LinkDto(
    @SerializedName("self") val self: String,
    @SerializedName("short") val short: String,
)