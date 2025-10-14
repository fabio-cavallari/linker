package com.fabiocavallari.linker.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.fabiocavallari.linker.presentation.state.sampleAliasA
import com.fabiocavallari.linker.presentation.util.shareText

@Composable
fun AliasDetailDialog(
    alias: String,
    short: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                shareText(context, short)
            }) {
                Text("Share")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        title = {
            Text(
                text = "Link shortened successfully",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Text(
                text = alias,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AliasDetailDialogPreview() {
    AliasDetailDialog(alias = sampleAliasA.alias, short = sampleAliasA.short) { }
}