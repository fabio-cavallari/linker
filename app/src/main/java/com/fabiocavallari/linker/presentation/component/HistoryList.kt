package com.fabiocavallari.linker.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.presentation.state.sampleHomeScreenUiState
import com.fabiocavallari.linker.presentation.util.openExternalLink
import com.fabiocavallari.linker.presentation.util.shareText

@Composable
fun HistoryList(
    items: List<Alias>,
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
        val context = LocalContext.current
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(items) { index, item ->
                Row(Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = item.alias,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = item.short,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                    IconButton(
                        onClick = { shareText(context, item.short) },
                        modifier = Modifier
                            .size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = { openExternalLink(context, item.original) },
                        modifier = Modifier
                            .size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Redirect",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
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
    HistoryList(sampleHomeScreenUiState.historyList.toList())
}

@Preview(showBackground = true)
@Composable
private fun HistoryListEmptyPreview() {
    HistoryList(emptyList())
}