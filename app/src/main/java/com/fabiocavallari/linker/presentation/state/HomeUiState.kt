package com.fabiocavallari.linker.presentation.state

enum class HomeState {
    LOADING,
    SUCCESS,
    ERROR,
}

data class HomeScreenUiState(
    val historyList: List<String> = listOf("item A", "item B"),
    val uiState: HomeState = HomeState.SUCCESS
)