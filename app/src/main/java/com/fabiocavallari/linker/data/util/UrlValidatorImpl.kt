package com.fabiocavallari.linker.data.util

import com.fabiocavallari.linker.domain.util.UrlValidator
import java.net.URL

class UrlValidatorImpl : UrlValidator {
    override fun isValid(url: String): Boolean {
        return try {
            URL(url)
            true
        } catch (e: Exception) {
            false
        }
    }
}