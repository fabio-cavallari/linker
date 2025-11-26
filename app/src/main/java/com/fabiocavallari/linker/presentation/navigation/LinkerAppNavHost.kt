package com.fabiocavallari.linker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.presentation.screen.AliasDetailScreen
import com.fabiocavallari.linker.presentation.screen.HomeScreen
import kotlin.reflect.typeOf

@Composable
fun LinkerAppNavHostController(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenRoute.HomeScreenRoute
    ) {
        composable<ScreenRoute.HomeScreenRoute> {
            HomeScreen(navController = navHostController)
        }
        composable<ScreenRoute.AliasDetailScreenRoute>(
            typeMap =
                mapOf(
                    typeOf<Alias>() to CustomNavType.AliasNavType
                )
        ) { argument ->
            val aliasDetailScreenRoute = argument.toRoute<ScreenRoute.AliasDetailScreenRoute>()
            AliasDetailScreen(alias = aliasDetailScreenRoute.alias)
        }
    }
}