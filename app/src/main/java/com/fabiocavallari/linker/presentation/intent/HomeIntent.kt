package com.fabiocavallari.linker.presentation.intent

sealed class HomeIntent {
    data object PostLink: HomeIntent()
}