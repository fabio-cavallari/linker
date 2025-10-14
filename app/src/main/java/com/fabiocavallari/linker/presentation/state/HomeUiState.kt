package com.fabiocavallari.linker.presentation.state

import com.fabiocavallari.linker.data.model.Error
import com.fabiocavallari.linker.domain.model.Alias

data class HomeScreenUiState(
    val historyList: LinkedHashSet<Alias> = linkedSetOf(),
    val isTextFieldLoading: Boolean = false,
    val link: String = "",
    val dialogError: Error? = null,
    val selectedAlias: Alias? = null,
)

val sampleAliasA = Alias(
    alias = "alias A",
    short = "short A",
    original = "original A"
)

val sampleAliasB = Alias(
    alias = "alias B",
    short = "short B",
    original = "original B"
)

val sampleAliasC = Alias(
    alias = "alias C",
    short = "short C",
    original = "original C"
)

val sampleHomeScreenUiState = HomeScreenUiState(
    historyList = linkedSetOf(sampleAliasA, sampleAliasB, sampleAliasC),
    link = "sample link",
)