package com.fabiocavallari.linker.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fabiocavallari.linker.presentation.component.ErrorDialog
import com.fabiocavallari.linker.presentation.component.HistoryList
import com.fabiocavallari.linker.presentation.component.LinkTextField
import com.fabiocavallari.linker.presentation.intent.HomeIntent
import com.fabiocavallari.linker.presentation.intent.HomeUiEffect
import com.fabiocavallari.linker.presentation.state.HomeScreenUiState
import com.fabiocavallari.linker.presentation.state.sampleHomeScreenUiState
import com.fabiocavallari.linker.presentation.viewmodel.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    var homeEffect: HomeUiEffect? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { effect ->
            when (effect) {
                is HomeUiEffect.OnSubmitLinkError -> homeEffect = effect
            }
        }
    }
    homeEffect?.let {
        val submitLinkError = (it as HomeUiEffect.OnSubmitLinkError)
        ErrorDialog(error = submitLinkError.error) { homeEffect = null }
    }

    HomeScreen(state) { homeIntent ->
        viewModel.onIntent(homeIntent)
    }
}

@Composable
fun HomeScreen(state: HomeScreenUiState, onIntent: (HomeIntent) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        LinkTextField(
            link = state.link,
            isLoading = state.isTextFieldLoading,
            onTextChanged = { text ->
                onIntent(HomeIntent.OnTextChanged(text))
            },
            onSubmitLink = { text ->
                onIntent(HomeIntent.OnSubmitLink(text))
            }
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Últimos links",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(Modifier.height(16.dp))
        HistoryList(state.historyList.toList()) { }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(sampleHomeScreenUiState, onIntent = {})
}