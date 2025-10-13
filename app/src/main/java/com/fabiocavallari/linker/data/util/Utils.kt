package com.fabiocavallari.linker.data.util

import com.fabiocavallari.linker.data.model.DataError
import com.fabiocavallari.linker.data.model.Result
import com.fabiocavallari.linker.data.model.Result.Error
import com.fabiocavallari.linker.data.model.Result.Success
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(
    call: suspend () -> Response<T>
): Result<T, DataError.Network> {
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
        Error(DataError.Network.REQUEST_TIMEOUT)
    } catch (e: IOException) {
        Error(DataError.Network.NO_CONNECTION)
    } catch (e: Exception) {
        Error(DataError.Network.UNKNOWN)
    }
}

fun mapHttpError(code: Int): DataError.Network = when (code) {
    400 -> DataError.Network.BAD_REQUEST
    404 -> DataError.Network.NOT_FOUND
    408 -> DataError.Network.REQUEST_TIMEOUT
    in 401..499 -> DataError.Network.CLIENT_ERROR
    500 -> DataError.Network.INTERNAL_SERVER_ERROR
    in 501..599 -> DataError.Network.SERVER_ERROR
    else -> DataError.Network.UNKNOWN
}