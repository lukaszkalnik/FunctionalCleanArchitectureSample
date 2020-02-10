package com.lukaszkalnik.functionalcleanarchitecture.domain.usecase

import arrow.core.Either.Right
import com.lukaszkalnik.functionalcleanarchitecture.data.model.Entity
import com.lukaszkalnik.functionalcleanarchitecture.data.remote.GatewayApi
import com.lukaszkalnik.functionalcleanarchitecture.domain.converter.EntityConverter
import com.lukaszkalnik.functionalcleanarchitecture.domain.model.Light
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import retrofit2.Response

class GetLightsUseCaseTest {

    private val testEntity = Entity(
        properties = emptyMap(),
        actions = null,
        entities = null
    )

    private val mockApi = mockk<GatewayApi>()

    private val lights = listOf(
        Light(
            id = "1234",
            name = "",
            actions = emptyList()
        )
    )

    private val mockConverter = mockk<EntityConverter<List<Light>>> converter@{
        every { this@converter.invoke(testEntity) } returns lights
    }

    private val useCase = getLightsUseCaseFactory(mockApi, mockConverter)

    @Test
    fun `calling use case with response success should return list of lights`() {
        val roomId = "room 1"
        coEvery { mockApi.getLights(roomId) } returns Response.success(testEntity)

        val result = runBlocking { useCase(roomId) }

        coVerifyAll { mockApi.getLights(roomId) }
        verifyAll { mockConverter(testEntity) }

        // In the functional programming [Either] type the convention is to store
        // the success value in the [Right] subtype and its value is stored under the [b] property.
        // Failure would be stored in the [Left] subtype under the [a] property.
        assertThat(result).isInstanceOf(Right::class.java)
        result as Right
        assertThat(result.b).isEqualTo(lights)
    }
}
