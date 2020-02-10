package com.lukaszkalnik.functionalcleanarchitecture.data.remote

import com.lukaszkalnik.functionalcleanarchitecture.data.model.Entity
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

internal interface GatewayApi {

    @GET("lights/{roomId}")
    suspend fun getLights(@Path("roomId") roomId: String): Response<Entity>

    @GET("system")
    suspend fun getSystemDetail(): Response<Entity>

    companion object {

        fun create(baseUrl: String): GatewayApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
            .create(GatewayApi::class.java)
    }
}
