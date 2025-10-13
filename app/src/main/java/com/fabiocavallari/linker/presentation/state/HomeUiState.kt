package com.fabiocavallari.linker.presentation.state

import com.fabiocavallari.linker.domain.model.Alias

enum class HomeState {
    LOADING,
    SUCCESS,
    ERROR,
}

data class HomeScreenUiState(
    val historyList: List<Alias> = emptyList(),
    val uiState: HomeState = HomeState.SUCCESS,
    val link: String = ""
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
    historyList = listOf(sampleAliasA, sampleAliasB, sampleAliasC),
    link = "sample link",
)