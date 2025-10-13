package com.fabiocavallari.linker.presentation.state

enum class HomeState {
    LOADING,
    SUCCESS,
    ERROR,
}

data class HomeScreenUiState(
    val historyList: List<String> = emptyList(),
    val uiState: HomeState = HomeState.SUCCESS,
    val link: String = ""
)

val sampleHomeScreenUiState = HomeScreenUiState(
    historyList = listOf("item A", "item B", "item C", "item D"),
    link = "sample link",
)