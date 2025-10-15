package com.fabiocavallari.linker.data.util

import com.fabiocavallari.linker.domain.model.AppError
import com.fabiocavallari.linker.domain.model.Result
import com.fabiocavallari.linker.domain.model.Result.Error
import com.fabiocavallari.linker.domain.model.Result.Success
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(
    call: suspend () -> Response<T>
): Result<T, AppError.Data> {
    return try {
        val response = call()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            Success(body)
        } else {
            Error(mapHttpError(response.code()))
        }
    } catch (e: HttpException) {
        Error(mapHttpError(e.code()))
    } catch (e: SocketTimeoutException) {
        Error(AppError.Data.REQUEST_TIMEOUT)
    } catch (e: IOException) {
        Error(AppError.Data.NO_CONNECTION)
    } catch (e: Exception) {
        Error(AppError.Data.UNKNOWN)
    }
}

fun mapHttpError(code: Int): AppError.Data = when (code) {
    400 -> AppError.Data.BAD_REQUEST
    404 -> AppError.Data.NOT_FOUND
    408 -> AppError.Data.REQUEST_TIMEOUT
    in 401..499 -> AppError.Data.CLIENT_ERROR
    500 -> AppError.Data.INTERNAL_SERVER_ERROR
    in 501..599 -> AppError.Data.SERVER_ERROR
    else -> AppError.Data.UNKNOWN
}