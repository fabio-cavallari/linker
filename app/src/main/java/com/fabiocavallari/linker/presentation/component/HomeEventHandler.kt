package com.fabiocavallari.linker.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.fabiocavallari.linker.presentation.intent.HomeEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeEventsHandler(
    homeEventFlow: Flow<HomeEvent>
) {
    var currentEvent: HomeEvent? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        homeEventFlow.collect { event ->
            when (event) {
                is HomeEvent.OpenErrorDialog -> currentEvent = event
            }
        }
    }
    currentEvent?.let { event ->
        when (event) {
            is HomeEvent.OpenErrorDialog -> {
                ErrorDialog(error = event.error) {
                    currentEvent = null
                }
            }
        }
    }
}