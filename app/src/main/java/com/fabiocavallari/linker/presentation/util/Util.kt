package com.fabiocavallari.linker.presentation.util

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun openExternalLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}

fun shareText(context: Context, text: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            text
        )
        type = "text/plain"
    }
    context.startActivity(
        Intent.createChooser(shareIntent, "Share")
    )
}