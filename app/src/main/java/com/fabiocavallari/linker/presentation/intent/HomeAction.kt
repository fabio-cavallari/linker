package com.fabiocavallari.linker.presentation.intent

import com.fabiocavallari.linker.data.model.Error

sealed class HomeAction {
    data class OnSubmitLink(val link: String): HomeAction()
    data class OnTextChanged(val link: String) : HomeAction()

}

sealed class HomeEvent {
    data class OpenErrorDialog(val error: Error) : HomeEvent()
}
