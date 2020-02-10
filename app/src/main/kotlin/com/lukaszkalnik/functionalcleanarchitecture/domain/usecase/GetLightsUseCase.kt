package com.lukaszkalnik.functionalcleanarchitecture.domain.usecase

import arrow.core.Either
import com.lukaszkalnik.functionalcleanarchitecture.data.remote.GatewayApi
import com.lukaszkalnik.functionalcleanarchitecture.domain.converter.EntityConverter
import com.lukaszkalnik.functionalcleanarchitecture.domain.extension.toEither
import com.lukaszkalnik.functionalcleanarchitecture.domain.model.Light

internal typealias GetLightsUseCase = suspend (String) -> Either<Exception, List<Light>>

internal fun getLightsUseCaseFactory(
    api: GatewayApi,
    converter: EntityConverter<List<Light>>
): GetLightsUseCase = { roomId: String ->
    api.getLights(roomId)
        .toEither()
        .map { converter(it) }
}
