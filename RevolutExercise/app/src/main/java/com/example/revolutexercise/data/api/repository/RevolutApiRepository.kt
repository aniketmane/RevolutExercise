package com.example.revolutexercise.data.api.repository

import com.example.revolutexercise.data.api.model.RatesResponse
import kotlinx.coroutines.flow.Flow


interface RevolutApiRepository {
    fun getRates(currency: String): Flow<RatesResponse>
}