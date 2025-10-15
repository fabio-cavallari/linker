package com.fabiocavallari.linker.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.fabiocavallari.linker.domain.model.AppError

@Composable
fun ErrorDialog(error: AppError, onDismiss: () -> Unit) {
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

fun getErrorMessage(error: AppError): String {
    return when (error) {
        is AppError.Data -> when (error) {
            AppError.Data.REQUEST_TIMEOUT -> "The request took too long to respond."
            AppError.Data.BAD_REQUEST -> "Invalid request. Please check the data you sent."
            AppError.Data.INTERNAL_SERVER_ERROR -> "Internal server error. Please try again later."
            AppError.Data.EMPTY_BODY -> "Unexpected server response."
            AppError.Data.NO_CONNECTION -> "No internet connection. Please check your network."
            else -> "An unknown error occurred."
        }
        else -> "An unknown error occurred."
    }
}
