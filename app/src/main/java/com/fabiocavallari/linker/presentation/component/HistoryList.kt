package com.fabiocavallari.linker.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.presentation.state.sampleHomeScreenUiState

@Composable
fun HistoryList(
    items: List<Alias>,
) {
    if (items.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.Link,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Your list is empty",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Shorten a link to see it here.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(items) { index, item ->
                HistoryListItem(item)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HistoryListPreview() {
    HistoryList(sampleHomeScreenUiState.historyList.toList())
}

@Preview(showBackground = true)
@Composable
private fun HistoryListEmptyPreview() {
    HistoryList(emptyList())
}