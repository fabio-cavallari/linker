package com.fabiocavallari.linker.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fabiocavallari.linker.presentation.navigation.LinkerAppNavHostController
import com.fabiocavallari.linker.presentation.theme.LinkerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentTitle = currentBackStackEntry?.destination?.route?.let { route ->
                when {
                    route.contains("HomeScreenRoute") -> "Linker"
                    route.contains("AliasDetailScreenRoute") -> "Alias Detail"
                    else -> "Linker"
                }
            } ?: "Linker"
            LinkerTheme {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            title = {
                                Text(currentTitle)
                            },
                        )
                    }) { innerPadding ->
                    Box(Modifier.Companion.padding(innerPadding)) {
                        LinkerAppNavHostController(navHostController)
                    }
                }
            }
        }
    }
}