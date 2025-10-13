package com.fabiocavallari.linker.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fabiocavallari.linker.presentation.state.sampleHomeScreenUiState

@Composable
fun HistoryList(
    items: List<String>,
    onItemClick: (String) -> Unit
) {
    if (items.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = "Sem links ainda...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(items) { index, item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(item) }
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                if (index < items.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryListPreview() {
    HistoryList(sampleHomeScreenUiState.historyList) { }
}

@Preview(showBackground = true)
@Composable
private fun HistoryListEmptyPreview() {
    HistoryList(emptyList()) { }
}