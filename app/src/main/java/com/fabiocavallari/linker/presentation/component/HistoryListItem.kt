package com.fabiocavallari.linker.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fabiocavallari.linker.domain.model.Alias
import com.fabiocavallari.linker.presentation.state.sampleAliasA
import com.fabiocavallari.linker.presentation.util.openExternalLink
import com.fabiocavallari.linker.presentation.util.shareText

@Composable
fun HistoryListItem(
    item: Alias
) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = item.alias,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item.short,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(
                onClick = { shareText(context, item.short) },
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share link",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { openExternalLink(context, item.original) },
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                    contentDescription = "Open link",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Preview
@Composable
private fun HistoryListItemPreview() {
    HistoryListItem(sampleAliasA)
}