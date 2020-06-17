package com.example.revolutexercise.data.api.repository

import com.example.revolutexercise.data.api.RevolutApiService
import com.example.revolutexercise.data.api.model.RatesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RevolutApiRepositoryImpl(private val apiService: RevolutApiService) :
    RevolutApiRepository {

    override fun getRates(currency: String): Flow<RatesResponse> =
        flow { emit(apiService.getRates(currency)) }


}