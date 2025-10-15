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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fabiocavallari.linker.R
import com.fabiocavallari.linker.presentation.screen.HomeScreen
import com.fabiocavallari.linker.presentation.theme.LinkerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                )
                            },
                        )
                    }) { innerPadding ->
                    Box(Modifier.Companion.padding(innerPadding)) {
                        HomeScreen()
                    }
                }
            }
        }
    }
}