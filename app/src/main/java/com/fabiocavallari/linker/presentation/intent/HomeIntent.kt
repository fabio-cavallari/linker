package com.fabiocavallari.linker.presentation.intent

import com.fabiocavallari.linker.domain.model.Alias

sealed class HomeIntent {
    data class OnSubmitLink(val link: String): HomeIntent()
    data class OnTextChanged(val link: String) : HomeIntent()
    data object OnDismissDialog : HomeIntent()
    data object OnDismissAliasDialog : HomeIntent()
    data class OnSelectAlias(val alias: Alias, val showTitle: Boolean = true) : HomeIntent()

}