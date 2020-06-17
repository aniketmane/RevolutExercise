package com.example.revolutexercise.data.local

import com.example.revolutexercise.data.local.entity.Rate

class RatesDatabaseHelperImpl(private val appDatabase: AppDatabase) : RatesDatabaseHelper {

    override suspend fun insertRate(rate: Rate) = appDatabase.ratesDao().insert(rate)

    override suspend fun update(rate: Rate): Int = appDatabase.ratesDao().update(rate)

    override suspend fun get(currency: String): Rate? = appDatabase.ratesDao().get(currency)

    override suspend fun clearAllRates() = appDatabase.ratesDao().clearAllRates()

    override suspend fun getAllRates(): List<Rate> = appDatabase.ratesDao().getAllRates()

}