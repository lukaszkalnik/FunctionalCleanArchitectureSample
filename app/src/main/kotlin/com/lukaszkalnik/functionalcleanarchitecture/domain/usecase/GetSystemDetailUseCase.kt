package com.lukaszkalnik.functionalcleanarchitecture.domain.usecase

import arrow.core.Either
import com.lukaszkalnik.functionalcleanarchitecture.data.remote.GatewayApi
import com.lukaszkalnik.functionalcleanarchitecture.domain.converter.EntityConverter
import com.lukaszkalnik.functionalcleanarchitecture.domain.extension.toEither
import com.lukaszkalnik.functionalcleanarchitecture.domain.model.SystemDetail

internal typealias GetSystemDetailUseCase = suspend () -> Either<Exception, SystemDetail>

internal fun getSystemDetailUseCaseFactory(
    api: GatewayApi,
    converter: EntityConverter<SystemDetail>
): GetSystemDetailUseCase = {
    api.getSystemDetail()
        .toEither()
        .map { converter(it) }
}
