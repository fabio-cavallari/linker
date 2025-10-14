package com.fabiocavallari.linker.presentation.intent

import com.fabiocavallari.linker.data.model.Error

sealed class HomeIntent {
    data class OnSubmitLink(val link: String): HomeIntent()
    data class OnTextChanged(val link: String) : HomeIntent()

}

sealed class HomeUiEffect {
    data class OnSubmitLinkError(val error: Error) : HomeUiEffect()
}
