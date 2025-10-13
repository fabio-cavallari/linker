package com.fabiocavallari.linker.presentation.intent

sealed class HomeIntent {
    data object PostLink: HomeIntent()
    data class OnTextChanged(val text: String) : HomeIntent()
}