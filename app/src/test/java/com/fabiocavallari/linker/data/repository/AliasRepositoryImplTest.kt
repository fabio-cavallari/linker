package com.fabiocavallari.linker.data.repository

import com.fabiocavallari.linker.data.model.DataError
import com.fabiocavallari.linker.data.model.Result
import com.fabiocavallari.linker.data.remoteprovider.UrlShortenerRemoteProvider
import com.fabiocavallari.linker.domain.model.Resource
import com.fabiocavallari.linker.domain.util.asDomainModel
import com.fabiocavallari.linker.shared.sampleAlias
import com.fabiocavallari.linker.shared.sampleAliasDto
import com.fabiocavallari.linker.shared.sampleInvalidUrl
import com.fabiocavallari.linker.shared.sampleUrl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AliasRepositoryImplTest {

    private lateinit var remoteProvider: UrlShortenerRemoteProvider
    private lateinit var repository: AliasRepositoryImpl

    @Before
    fun setup() {
        remoteProvider = mockk()
        repository = AliasRepositoryImpl(remoteProvider)
    }

    @Test
    fun `createAlias should return Success when remoteProvider returns Success`() = runTest {
        // Given
        val url = sampleUrl
        val aliasDto = sampleAliasDto
        val expectedAlias = sampleAlias

        mockkStatic("com.fabiocavallari.linker.domain.util.MapperKt")
        every { aliasDto.asDomainModel() } returns expectedAlias

        coEvery { remoteProvider.createAlias(url) } returns Result.Success(aliasDto)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Success)
        assertEquals(expectedAlias, (result as Resource.Success).data)
        coVerify(exactly = 1) { remoteProvider.createAlias(url) }
    }

    @Test
    fun `createAlias should return Error INVALID_URL when url is invalid`() = runTest {
        // Given
        val url = sampleInvalidUrl
        val expectedError = DataError.Local.INVALID_URL

        coEvery { remoteProvider.createAlias(url) } returns Result.Success(mockk())

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(expectedError, (result as Resource.Error).error)
        coVerify(exactly = 0) { remoteProvider.createAlias(url) }
    }

    @Test
    fun `createAlias should return Error when remoteProvider returns Error NOT_FOUND`() = runTest {
        // Given
        val url = sampleUrl
        val networkError = DataError.Network.NOT_FOUND

        coEvery { remoteProvider.createAlias(url) } returns Result.Error(networkError)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(networkError, (result as Resource.Error).error)
        coVerify(exactly = 1) { remoteProvider.createAlias(url) }
    }

    @Test
    fun `createAlias should return Error when remoteProvider returns Error BAD_REQUEST`() = runTest {
        // Given
        val url = sampleUrl
        val networkError = DataError.Network.BAD_REQUEST

        coEvery { remoteProvider.createAlias(url) } returns Result.Error(networkError)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(networkError, (result as Resource.Error).error)
    }

    @Test
    fun `createAlias should return Error when remoteProvider returns Error REQUEST_TIMEOUT`() = runTest {
        // Given
        val url = sampleUrl
        val networkError = DataError.Network.REQUEST_TIMEOUT

        coEvery { remoteProvider.createAlias(url) } returns Result.Error(networkError)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(networkError, (result as Resource.Error).error)
    }

    @Test
    fun `createAlias should return Error when remoteProvider returns Error NO_CONNECTION`() = runTest {
        // Given
        val url = sampleUrl
        val networkError = DataError.Network.NO_CONNECTION

        coEvery { remoteProvider.createAlias(url) } returns Result.Error(networkError)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(networkError, (result as Resource.Error).error)
    }

    @Test
    fun `createAlias should return Error when remoteProvider returns Error INTERNAL_SERVER_ERROR`() = runTest {
        // Given
        val url = sampleUrl
        val networkError = DataError.Network.INTERNAL_SERVER_ERROR

        coEvery { remoteProvider.createAlias(url) } returns Result.Error(networkError)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(networkError, (result as Resource.Error).error)
    }

    @Test
    fun `createAlias should return Error when remoteProvider returns Error SERVER_ERROR`() = runTest {
        // Given
        val url = sampleUrl
        val networkError = DataError.Network.SERVER_ERROR

        coEvery { remoteProvider.createAlias(url) } returns Result.Error(networkError)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(networkError, (result as Resource.Error).error)
    }

    @Test
    fun `createAlias should return Error when remoteProvider returns Error CLIENT_ERROR`() = runTest {
        // Given
        val url = sampleUrl
        val networkError = DataError.Network.CLIENT_ERROR

        coEvery { remoteProvider.createAlias(url) } returns Result.Error(networkError)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(networkError, (result as Resource.Error).error)
    }

    @Test
    fun `createAlias should return Error when remoteProvider returns Error UNKNOWN`() = runTest {
        // Given
        val url = sampleUrl
        val networkError = DataError.Network.UNKNOWN

        coEvery { remoteProvider.createAlias(url) } returns Result.Error(networkError)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(networkError, (result as Resource.Error).error)
    }

    @Test
    fun `createAlias should return Error with UNKNOWN when exception is thrown`() = runTest {
        // Given
        val url = sampleUrl
        val exceptionMessage = "Unexpected error"

        coEvery { remoteProvider.createAlias(url) } throws RuntimeException(exceptionMessage)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(DataError.Network.UNKNOWN, (result as Resource.Error).error)
        assertEquals(exceptionMessage, result.message)
    }

    @Test
    fun `createAlias should return Error with null message when exception has no message`() = runTest {
        // Given
        val url = sampleUrl

        coEvery { remoteProvider.createAlias(url) } throws RuntimeException()

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Error)
        assertEquals(DataError.Network.UNKNOWN, (result as Resource.Error).error)
        assertEquals(null, result.message)
    }

    @Test
    fun `createAlias should correctly transform DTO to domain model`() = runTest {
        // Given
        val url = sampleUrl
        val aliasDto = sampleAliasDto
        val alias = sampleAlias

        mockkStatic("com.fabiocavallari.linker.domain.util.MapperKt")
        every { aliasDto.asDomainModel() } returns alias

        coEvery { remoteProvider.createAlias(url) } returns Result.Success(aliasDto)

        // When
        val result = repository.createAlias(url)

        // Then
        assertTrue(result is Resource.Success)
        val successResult = result as Resource.Success
        assertEquals(alias.alias, successResult.data.alias)
        assertEquals(alias.short, successResult.data.short)
        assertEquals(alias.original, successResult.data.original)
    }
}