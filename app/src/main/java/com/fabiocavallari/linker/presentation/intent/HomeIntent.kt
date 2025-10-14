package com.fabiocavallari.linker.presentation.intent

sealed class HomeIntent {
    data class OnSubmitLink(val link: String): HomeIntent()
    data class OnTextChanged(val link: String) : HomeIntent()
    data object OnDismissDialog : HomeIntent()
    data object OnDismissAliasDialog : HomeIntent()

}