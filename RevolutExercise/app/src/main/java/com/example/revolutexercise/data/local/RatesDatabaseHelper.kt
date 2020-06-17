package com.example.revolutexercise.data.local

import com.example.revolutexercise.data.local.entity.Rate

interface RatesDatabaseHelper {

    suspend fun insertRate(rate: Rate): Long

    suspend fun update(rate: Rate): Int

    suspend fun get(currency: String): Rate?

    suspend fun clearAllRates()

    suspend fun getAllRates(): List<Rate>

}