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
            DataError.Network.REQUEST_TIMEOUT -> "A requisição demorou demais para responder."
            DataError.Network.BAD_REQUEST -> "Requisição inválida. Verifique os dados enviados."
            DataError.Network.INTERNAL_SERVER_ERROR -> "Erro interno no servidor. Tente novamente mais tarde."
            DataError.Network.EMPTY_BODY -> "Resposta inesperada do servidor (sem conteúdo)."
            DataError.Network.NO_CONNECTION -> "Sem conexão com a internet. Verifique sua rede."
            else -> "Ocorreu um erro desconhecido."
        }
        else -> "Ocorreu um erro desconhecido."
    }
}
