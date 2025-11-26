package com.fabiocavallari.linker.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fabiocavallari.linker.domain.model.Alias

@Composable
fun AliasDetailScreen(modifier: Modifier = Modifier, alias: Alias) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("alias detail - ${alias.alias}")
    }
}