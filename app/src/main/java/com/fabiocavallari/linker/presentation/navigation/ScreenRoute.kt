package com.fabiocavallari.linker.presentation.navigation

import com.fabiocavallari.linker.domain.model.Alias
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute() {
    @Serializable
    data object HomeScreenRoute: ScreenRoute()
    @Serializable
    data class AliasDetailScreenRoute(val alias: Alias): ScreenRoute()
    @Serializable
    data class DeeplinkScreen(val id: Int): ScreenRoute()
}