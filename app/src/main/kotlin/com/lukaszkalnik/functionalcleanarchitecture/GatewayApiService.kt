package com.lukaszkalnik.functionalcleanarchitecture

import arrow.core.Either
import com.lukaszkalnik.functionalcleanarchitecture.data.remote.GatewayApi
import com.lukaszkalnik.functionalcleanarchitecture.domain.converter.ConverterTag
import com.lukaszkalnik.functionalcleanarchitecture.domain.converter.resolveConverter
import com.lukaszkalnik.functionalcleanarchitecture.domain.model.Light
import com.lukaszkalnik.functionalcleanarchitecture.domain.model.SystemDetail
import com.lukaszkalnik.functionalcleanarchitecture.domain.usecase.GetLightsUseCase
import com.lukaszkalnik.functionalcleanarchitecture.domain.usecase.GetSystemDetailUseCase
import com.lukaszkalnik.functionalcleanarchitecture.domain.usecase.getLightsUseCaseFactory
import com.lukaszkalnik.functionalcleanarchitecture.domain.usecase.getSystemDetailUseCaseFactory

/**
 * Main entry point from the client code to the library.
 */
interface GatewayApiService {

    /**
     * Gets list of [Light]s for the specified [roomId]
     */
    suspend fun getLights(roomId: String): Either<Throwable, List<Light>>

    /**
     * Gets [SystemDetail] of the current system.
     */
    suspend fun getSystemDetail(): Either<Throwable, SystemDetail>

    companion object {

        /**
         * A factory function to create an instance of [GatewayApiService] for calling from the client code.
         *
         * Note that this is also where the central dependency injection is done.
         *
         * This function creates a [GatewayApi] instance from the data layer, resolves the converters and injects them into the
         * use case factory functions to create use case instances.
         *
         * It then creates an instance of its own implementation, [DefaultGatewayApiService], injecting created
         * use cases. This instance is then returned to the caller.
         *
         * This dependency graph resolution might be done equally well outside of this class. You might have placed it
         * in an external singleton "injector" file or even used a DI framework for this. It is located here just
         * for namespacing.
         */
        fun with(baseUrl: String): GatewayApiService {
            val api = GatewayApi.create(baseUrl)

            val lightsConverter = resolveConverter<List<Light>>(ConverterTag.LIGHT_LIST)
            // TODO this will currently crash the app when calling the [getSystemDetail] endpoint
            // because we haven't registered any converter for [ConverterTag.SYSTEM_DETAIL].
            val systemDetailConverter = resolveConverter<SystemDetail>(ConverterTag.SYSTEM_DETAIL)

            return DefaultGatewayApiService(
                getLightsUseCase = getLightsUseCaseFactory(api, lightsConverter),
                getSystemDetailUseCase = getSystemDetailUseCaseFactory(api, systemDetailConverter)
            )
        }
    }
}

internal class DefaultGatewayApiService(
    val getLightsUseCase: GetLightsUseCase,
    val getSystemDetailUseCase: GetSystemDetailUseCase
) : GatewayApiService {

    override suspend fun getLights(roomId: String): Either<Throwable, List<Light>> = getLightsUseCase(roomId)

    override suspend fun getSystemDetail(): Either<Throwable, SystemDetail> = getSystemDetailUseCase()
}
