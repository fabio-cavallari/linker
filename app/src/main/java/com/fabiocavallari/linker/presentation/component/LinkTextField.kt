package com.fabiocavallari.linker.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LinkTextField(link: String = "", onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = link,
        onValueChange = { newText ->
            onTextChanged(newText)
        },
        placeholder = { Text("Digite seu link...") },
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        label = { Text("Link") },
        shape = RoundedCornerShape(60.dp),
        maxLines = 1,
        trailingIcon = {
            val isEnabled = link.isNotBlank()
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