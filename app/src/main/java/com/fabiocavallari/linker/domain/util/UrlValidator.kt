package com.fabiocavallari.linker.domain.util

interface UrlValidator {
    fun isValid(url: String): Boolean
}