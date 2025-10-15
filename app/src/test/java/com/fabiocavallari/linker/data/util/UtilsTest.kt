package com.fabiocavallari.linker.data.util

import com.fabiocavallari.linker.data.model.DataError
import com.fabiocavallari.linker.data.model.Result
import com.fabiocavallari.linker.shared.sampleInvalidUrl
import com.fabiocavallari.linker.shared.sampleUrl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class SafeApiCallTest {

    @Test
    fun `safeApiCall should return Success when response is successful with body`() = runTest {
        // Given
        val expectedData = "test data"
        val response = Response.success(expectedData)

        // When
        val result = safeApiCall { response }

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedData, (result as Result.Success).data)
    }

    @Test
    fun `safeApiCall should return Error when response is successful but body is null`() = runTest {
        // Given
        val response = Response.success<String>(null)

        // When
        val result = safeApiCall { response }

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.UNKNOWN, (result as Result.Error).error)
    }

    @Test
    fun `safeApiCall should return Error with mapped code when response is not successful`() = runTest {
        // Given
        val response = Response.error<String>(404, mockk(relaxed = true))

        // When
        val result = safeApiCall { response }

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.NOT_FOUND, (result as Result.Error).error)
    }

    @Test
    fun `safeApiCall should return Error when HttpException is thrown`() = runTest {
        // Given
        val httpException = mockk<HttpException> {
            coEvery { code() } returns 500
        }

        // When
        val result = safeApiCall<String> { throw httpException }

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.INTERNAL_SERVER_ERROR, (result as Result.Error).error)
    }

    @Test
    fun `safeApiCall should return REQUEST_TIMEOUT when SocketTimeoutException is thrown`() = runTest {
        // Given
        val exception = SocketTimeoutException("Timeout")

        // When
        val result = safeApiCall<String> { throw exception }

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.REQUEST_TIMEOUT, (result as Result.Error).error)
    }

    @Test
    fun `safeApiCall should return NO_CONNECTION when IOException is thrown`() = runTest {
        // Given
        val exception = IOException("No internet")

        // When
        val result = safeApiCall<String> { throw exception }

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.NO_CONNECTION, (result as Result.Error).error)
    }

    @Test
    fun `safeApiCall should return UNKNOWN when generic Exception is thrown`() = runTest {
        // Given
        val exception = RuntimeException("Unknown error")

        // When
        val result = safeApiCall<String> { throw exception }

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.UNKNOWN, (result as Result.Error).error)
    }

    @Test
    fun `mapHttpError should return BAD_REQUEST for code 400`() {
        assertEquals(DataError.Network.BAD_REQUEST, mapHttpError(400))
    }

    @Test
    fun `mapHttpError should return NOT_FOUND for code 404`() {
        assertEquals(DataError.Network.NOT_FOUND, mapHttpError(404))
    }

    @Test
    fun `mapHttpError should return REQUEST_TIMEOUT for code 408`() {
        assertEquals(DataError.Network.REQUEST_TIMEOUT, mapHttpError(408))
    }

    @Test
    fun `mapHttpError should return CLIENT_ERROR for codes 401 to 499`() {
        assertEquals(DataError.Network.CLIENT_ERROR, mapHttpError(401))
        assertEquals(DataError.Network.CLIENT_ERROR, mapHttpError(403))
        assertEquals(DataError.Network.CLIENT_ERROR, mapHttpError(450))
        assertEquals(DataError.Network.CLIENT_ERROR, mapHttpError(499))
    }

    @Test
    fun `mapHttpError should return INTERNAL_SERVER_ERROR for code 500`() {
        assertEquals(DataError.Network.INTERNAL_SERVER_ERROR, mapHttpError(500))
    }

    @Test
    fun `mapHttpError should return SERVER_ERROR for codes 501 to 599`() {
        assertEquals(DataError.Network.SERVER_ERROR, mapHttpError(501))
        assertEquals(DataError.Network.SERVER_ERROR, mapHttpError(550))
        assertEquals(DataError.Network.SERVER_ERROR, mapHttpError(599))
    }

    @Test
    fun `mapHttpError should return UNKNOWN for unhandled codes`() {
        assertEquals(DataError.Network.UNKNOWN, mapHttpError(600))
        assertEquals(DataError.Network.UNKNOWN, mapHttpError(300))
        assertEquals(DataError.Network.UNKNOWN, mapHttpError(100))
    }

    @Test
    fun `isValidUrl should return true for valid HTTP URL`() {
        // Given
        val url = sampleUrl

        // When
        val result = isValidUrl(url)

        // Then
        assertTrue(result)
    }


    @Test
    fun `isValidUrl should return false for empty string`() {
        // Given
        val url = ""

        // When
        val result = isValidUrl(url)

        // Then
        assertFalse(result)
    }

    @Test
    fun `isValidUrl should return false for invalid URL format`() {
        // Given
        val url = sampleInvalidUrl

        // When
        val result = isValidUrl(url)

        // Then
        assertFalse(result)
    }

}