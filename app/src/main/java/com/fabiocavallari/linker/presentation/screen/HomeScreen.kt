package com.fabiocavallari.linker.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fabiocavallari.linker.presentation.component.HistoryList
import com.fabiocavallari.linker.presentation.component.HomeEventsHandler
import com.fabiocavallari.linker.presentation.component.LinkTextField
import com.fabiocavallari.linker.presentation.intent.HomeAction
import com.fabiocavallari.linker.presentation.state.HomeScreenUiState
import com.fabiocavallari.linker.presentation.state.sampleHomeScreenUiState
import com.fabiocavallari.linker.presentation.viewmodel.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeEventsHandler(viewModel.uiEvent)
    HomeScreen(state) { homeIntent ->
        viewModel.onIntent(homeIntent)
    }
}

@Composable
fun HomeScreen(state: HomeScreenUiState, onIntent: (HomeAction) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        LinkTextField(
            link = state.link,
            isLoading = state.isTextFieldLoading,
            onTextChanged = { text ->
                onIntent(HomeAction.OnTextChanged(text))
            },
            onSubmitLink = { text ->
                onIntent(HomeAction.OnSubmitLink(text))
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