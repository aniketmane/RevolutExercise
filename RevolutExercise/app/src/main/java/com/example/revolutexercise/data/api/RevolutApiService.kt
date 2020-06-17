package com.example.revolutexercise.data.api

import com.example.revolutexercise.data.api.model.RatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutApiService {

    @GET("latest")
    suspend fun getRates(@Query("base") currency: String): RatesResponse
}