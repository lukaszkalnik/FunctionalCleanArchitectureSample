package com.lukaszkalnik.functionalcleanarchitecture.domain.extension

import arrow.core.Either
import arrow.core.Either.Right
import arrow.core.Left
import retrofit2.Response

/**
 * Converts a Retrofit [Response] to an [Either] instance.
 *
 * Returns [Right] containing the response body in case of success.
 * Otherwise returns [Left] containing an exception with the error cause.
 */
fun <T> Response<T>.toEither(): Either<Exception, T> =
    if (isSuccessful) Right(body()!!) else Left(Exception(errorBody()?.string() ?: "Unknown error"))
