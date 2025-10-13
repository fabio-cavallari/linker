package com.fabiocavallari.linker.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fabiocavallari.linker.presentation.component.HistoryList
import com.fabiocavallari.linker.presentation.intent.HomeIntent
import com.fabiocavallari.linker.presentation.state.HomeScreenUiState
import com.fabiocavallari.linker.presentation.viewmodel.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    HomeScreen(state) { homeIntent ->
        viewModel.onIntent(homeIntent)
    }
}

@Composable
fun HomeScreen(state: HomeScreenUiState, onIntent: (HomeIntent) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = state.link,
                onValueChange = { newText ->
                    onIntent(HomeIntent.OnTextChanged(text = newText))
                },
                placeholder = { Text("Digite seu link...") },
                modifier = Modifier
                    .weight(1f)
                    .height(68.dp),
                label = { Text("Link") },
                shape = RoundedCornerShape(60.dp),
                maxLines = 1,
                trailingIcon = {
                    val isEnabled = state.link.isNotBlank()
                    IconButton(
                        onClick = { },
                        enabled = isEnabled,
                        modifier = Modifier
                            .size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Enviar",
                            tint = if (isEnabled)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                },
                singleLine = true,
            )
        }
        Spacer(Modifier.height(16.dp))
        HistoryList(state.historyList) { }
    }
}