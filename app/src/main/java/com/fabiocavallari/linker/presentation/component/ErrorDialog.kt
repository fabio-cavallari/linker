package com.fabiocavallari.linker.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.fabiocavallari.linker.data.model.DataError
import com.fabiocavallari.linker.data.model.Error

@Composable
fun ErrorDialog(error: Error, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
        text = { Text(getErrorMessage(error)) },
    )
}

fun getErrorMessage(error: Error): String {
    return when (error) {
        is DataError.Network -> when (error) {
            DataError.Network.REQUEST_TIMEOUT -> "The request took too long to respond."
            DataError.Network.BAD_REQUEST -> "Invalid request. Please check the data you sent."
            DataError.Network.INTERNAL_SERVER_ERROR -> "Internal server error. Please try again later."
            DataError.Network.EMPTY_BODY -> "Unexpected server response."
            DataError.Network.NO_CONNECTION -> "No internet connection. Please check your network."
            else -> "An unknown error occurred."
        }
        else -> "An unknown error occurred."
    }
}
