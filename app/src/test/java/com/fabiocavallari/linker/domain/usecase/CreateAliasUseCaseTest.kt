package com.fabiocavallari.linker.domain.usecase

import com.fabiocavallari.linker.data.util.UrlValidatorImpl
import com.fabiocavallari.linker.domain.model.AppError
import com.fabiocavallari.linker.domain.model.Resource
import com.fabiocavallari.linker.domain.repository.AliasRepository
import com.fabiocavallari.linker.domain.util.UrlValidator
import com.fabiocavallari.linker.shared.sampleAlias
import com.fabiocavallari.linker.shared.sampleInvalidUrl
import com.fabiocavallari.linker.shared.sampleUrl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CreateAliasUseCaseTest {

    private lateinit var repository: AliasRepository
    private lateinit var urlValidator: UrlValidator
    private lateinit var useCase: CreateAliasUseCase

    @Before
    fun setup() {
        repository = mockk()
        urlValidator = UrlValidatorImpl()
        useCase = CreateAliasUseCase(repository, urlValidator)
    }

    @Test
    fun `invoke should return Success when url is valid and repository returns Success`() =
        runTest {
            // Given
            val url = sampleUrl
            val expectedAlias = sampleAlias

            coEvery { repository.createAlias(url) } returns Resource.Success(expectedAlias)

            // When
            val result = useCase(url)

            // Then
            Assert.assertTrue(result is Resource.Success)
            Assert.assertEquals(expectedAlias, (result as Resource.Success).data)
            coVerify(exactly = 1) { repository.createAlias(url) }
        }

    @Test
    fun `invoke should return Error INVALID_URL when url is invalid and not call repository`() =
        runTest {
            // Given
            val invalidUrl = sampleInvalidUrl

            // When
            val result = useCase(invalidUrl)

            // Then
            Assert.assertTrue(result is Resource.Error)
            Assert.assertEquals(AppError.Domain.INVALID_URL, (result as Resource.Error).error)
            coVerify(exactly = 0) { repository.createAlias(any()) }
        }

    @Test
    fun `invoke should return Error when repository returns Network error`() = runTest {
        // Given
        val url = sampleUrl

        coEvery { repository.createAlias(url) } returns
                Resource.Error(AppError.Data.NO_CONNECTION)

        // When
        val result = useCase(url)

        // Then
        Assert.assertTrue(result is Resource.Error)
        Assert.assertEquals(AppError.Data.NO_CONNECTION, (result as Resource.Error).error)
        coVerify(exactly = 1) { repository.createAlias(url) }
    }

    @Test
    fun `invoke should return Error with message when repository returns error with message`() =
        runTest {
            // Given
            val url = sampleUrl
            val errorMessage = "error message"

            coEvery { repository.createAlias(url) } returns
                    Resource.Error(AppError.Data.UNKNOWN, errorMessage)

            // When
            val result = useCase(url)

            // Then
            Assert.assertTrue(result is Resource.Error)
            Assert.assertEquals(AppError.Data.UNKNOWN, (result as Resource.Error).error)
            Assert.assertEquals(errorMessage, result.message)
        }
}