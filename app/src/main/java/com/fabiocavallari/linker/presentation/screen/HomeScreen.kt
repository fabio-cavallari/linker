package com.fabiocavallari.linker.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AirplaneTicket
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.fabiocavallari.linker.presentation.component.AliasDetailDialog
import com.fabiocavallari.linker.presentation.component.ErrorDialog
import com.fabiocavallari.linker.presentation.component.HistoryList
import com.fabiocavallari.linker.presentation.component.LinkTextField
import com.fabiocavallari.linker.presentation.intent.HomeIntent
import com.fabiocavallari.linker.presentation.navigation.ScreenRoute
import com.fabiocavallari.linker.presentation.state.HomeScreenUiState
import com.fabiocavallari.linker.presentation.state.sampleHomeScreenUiState
import com.fabiocavallari.linker.presentation.viewmodel.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel(), navController: NavController) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(state) { homeIntent ->
        when (homeIntent) {
            is HomeIntent.OnNavigateToAliasDetail -> navController.navigate(
                route = ScreenRoute.AliasDetailScreenRoute(
                    alias = homeIntent.alias
                )
            )

            else -> viewModel.onIntent(homeIntent)
        }
    }
}

@Composable
fun HomeScreen(state: HomeScreenUiState, onIntent: (HomeIntent) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp),

        ) {
            state.dialogError?.let { errorResource ->
                ErrorDialog(errorResource.error) { onIntent(HomeIntent.OnDismissDialog) }
            }
            state.selectedAlias?.let { alias ->
                AliasDetailDialog(
                    alias = alias.alias,
                    short = alias.short,
                ) { onIntent(HomeIntent.OnDismissAliasDialog) }
            }
            LinkTextField(
                link = state.link,
                isLoading = state.isTextFieldLoading,
                isInvalidUrl = state.isInvalidUrl,
                onTextChanged = { text ->
                    onIntent(HomeIntent.OnTextChanged(text))
                },
                onSubmitLink = { text ->
                    onIntent(HomeIntent.OnSubmitLink(text))
                }
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Previous Links",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            HistoryList(
                modifier = Modifier.weight(1f),
                items = state.historyList.toList(),
                onAliasClick = { alias ->
                    onIntent(HomeIntent.OnNavigateToAliasDetail(alias))
                }
            )
            Spacer(Modifier.height(8.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            ) {
                Text("screen b")
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { Log.d(">>>", "fab click")}
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.AirplaneTicket,
                contentDescription = "fab button"
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(sampleHomeScreenUiState, onIntent = {})
}