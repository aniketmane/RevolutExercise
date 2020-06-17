package com.example.revolutexercise.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.revolutexercise.data.local.constants.DatabaseConstants
import com.example.revolutexercise.data.local.entity.Rate

@Dao
interface RatesDao {

    @Insert
    fun insert(rate: Rate): Long

    @Update
    fun update(rate: Rate): Int

    @Query("SELECT * from rate_table WHERE currency_rate LIKE :code")
    fun get(code: String): Rate?

    @Query("DELETE FROM " + DatabaseConstants.TABLE_NAME)
    fun clearAllRates()

    @Query("SELECT * FROM " + DatabaseConstants.TABLE_NAME + " ORDER BY CASE WHEN currency_rate = 1.00 THEN 0 ELSE 1 END")
    fun getAllRates(): List<Rate>
}

