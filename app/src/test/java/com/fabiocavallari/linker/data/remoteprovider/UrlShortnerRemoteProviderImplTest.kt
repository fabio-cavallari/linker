package com.fabiocavallari.linker.data.remoteprovider

import com.fabiocavallari.linker.data.client.UrlShortenerClient
import com.fabiocavallari.linker.data.model.AliasDto
import com.fabiocavallari.linker.data.model.CreateAliasRequestDto
import com.fabiocavallari.linker.data.model.DataError
import com.fabiocavallari.linker.data.model.Result
import com.fabiocavallari.linker.shared.sampleAliasDto
import com.fabiocavallari.linker.shared.sampleUrl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class UrlShortenerRemoteProviderImplTest {

    private lateinit var client: UrlShortenerClient
    private lateinit var provider: UrlShortenerRemoteProviderImpl

    @Before
    fun setup() {
        client = mockk()
        provider = UrlShortenerRemoteProviderImpl(client)
    }

    @Test
    fun `createAlias should return Success when client returns successful response`() = runTest {
        // Given
        val url = sampleUrl
        val expectedAlias = sampleAliasDto
        val request = CreateAliasRequestDto(url)
        val response = Response.success(expectedAlias)

        coEvery { client.createAlias(request) } returns response

        // When
        val result = provider.createAlias(url)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedAlias, (result as Result.Success).data)
        coVerify(exactly = 1) { client.createAlias(request) }
    }

    @Test
    fun `createAlias should return Error when client returns error response`() = runTest {
        // Given
        val url = sampleUrl
        val request = CreateAliasRequestDto(url)
        val errorResponse = Response.error<AliasDto>(404, mockk(relaxed = true))

        coEvery { client.createAlias(request) } returns errorResponse

        // When
        val result = provider.createAlias(url)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.NOT_FOUND, (result as Result.Error).error)
        coVerify(exactly = 1) { client.createAlias(request) }
    }

    @Test
    fun `createAlias should return Error when client returns null body`() = runTest {
        // Given
        val url = sampleUrl
        val request = CreateAliasRequestDto(url)
        val response = Response.success<AliasDto>(null)

        coEvery { client.createAlias(request) } returns response

        // When
        val result = provider.createAlias(url)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.UNKNOWN, (result as Result.Error).error)
    }

    @Test
    fun `createAlias should return BAD_REQUEST when client returns 400`() = runTest {
        // Given
        val url = sampleUrl
        val request = CreateAliasRequestDto(url)
        val errorResponse = Response.error<AliasDto>(400, mockk(relaxed = true))

        coEvery { client.createAlias(request) } returns errorResponse

        // When
        val result = provider.createAlias(url)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.BAD_REQUEST, (result as Result.Error).error)
    }

    @Test
    fun `createAlias should return INTERNAL_SERVER_ERROR when client returns 500`() = runTest {
        // Given
        val url = sampleUrl
        val request = CreateAliasRequestDto(url)
        val errorResponse = Response.error<AliasDto>(500, mockk(relaxed = true))

        coEvery { client.createAlias(request) } returns errorResponse

        // When
        val result = provider.createAlias(url)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.INTERNAL_SERVER_ERROR, (result as Result.Error).error)
    }

    @Test
    fun `createAlias should return REQUEST_TIMEOUT when SocketTimeoutException is thrown`() = runTest {
        // Given
        val url = sampleUrl
        val request = CreateAliasRequestDto(url)

        coEvery { client.createAlias(request) } throws SocketTimeoutException("Timeout")

        // When
        val result = provider.createAlias(url)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.REQUEST_TIMEOUT, (result as Result.Error).error)
    }

    @Test
    fun `createAlias should return NO_CONNECTION when IOException is thrown`() = runTest {
        // Given
        val url = sampleUrl
        val request = CreateAliasRequestDto(url)

        coEvery { client.createAlias(request) } throws IOException("No internet")

        // When
        val result = provider.createAlias(url)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.NO_CONNECTION, (result as Result.Error).error)
    }

    @Test
    fun `createAlias should return mapped error when HttpException is thrown`() = runTest {
        // Given
        val url = sampleUrl
        val request = CreateAliasRequestDto(url)
        val httpException = mockk<HttpException> {
            coEvery { code() } returns 401
        }

        coEvery { client.createAlias(request) } throws httpException

        // When
        val result = provider.createAlias(url)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.CLIENT_ERROR, (result as Result.Error).error)
    }

    @Test
    fun `createAlias should return UNKNOWN when generic Exception is thrown`() = runTest {
        // Given
        val url = sampleUrl
        val request = CreateAliasRequestDto(url)

        coEvery { client.createAlias(request) } throws RuntimeException("Unknown error")

        // When
        val result = provider.createAlias(url)

        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.UNKNOWN, (result as Result.Error).error)
    }

    @Test
    fun `createAlias should create correct request DTO with provided url`() = runTest {
        // Given
        val url = sampleUrl
        val expectedAlias = sampleAliasDto
        val response = Response.success(expectedAlias)

        coEvery { client.createAlias(any()) } returns response

        // When
        provider.createAlias(url)

        // Then
        coVerify {
            client.createAlias(
                match { it.url == url }
            )
        }
    }
}