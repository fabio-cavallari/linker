package com.fabiocavallari.linker.data.util

import com.fabiocavallari.linker.shared.sampleInvalidUrl
import com.fabiocavallari.linker.shared.sampleUrl
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UrlValidatorImplTest {
    private val urlValidator = UrlValidatorImpl()


    @Test
    fun `isValidUrl should return true for valid HTTP URL`() {
        // Given
        val url = sampleUrl

        // When
        val result = urlValidator.isValid(url)

        // Then
        assertTrue(result)
    }


    @Test
    fun `isValidUrl should return false for empty string`() {
        // Given
        val url = ""

        // When
        val result = urlValidator.isValid(url)

        // Then
        assertFalse(result)
    }

    @Test
    fun `isValidUrl should return false for invalid URL format`() {
        // Given
        val url = sampleInvalidUrl

        // When
        val result = urlValidator.isValid(url)

        // Then
        assertFalse(result)
    }
}