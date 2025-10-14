package com.fabiocavallari.linker.presentation.component

import android.content.Intent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.fabiocavallari.linker.presentation.state.sampleAliasA

@Composable
fun AliasDetailDialog(
    showTitle: Boolean = true,
    alias: String,
    original: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        original
                    )
                    type = "text/plain"
                }
                context.startActivity(
                    Intent.createChooser(shareIntent, "Compartilhar")
                )
            }) {
                Text("Compartilhar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        },
        title = {
            if (showTitle) {
                Text(
                    text = "Link encurtado com sucesso",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
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
private fun AliasDetailDialogWithTitlePreview() {
    AliasDetailDialog(alias = sampleAliasA.alias, original = sampleAliasA.original) { }
}

@Preview(showBackground = true)
@Composable
private fun AliasDetailDialogPreview() {
    AliasDetailDialog(showTitle = false, alias = sampleAliasA.alias, original = sampleAliasA.original) { }
}