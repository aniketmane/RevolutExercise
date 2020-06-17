package com.example.revolutexercise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.revolutexercise.data.local.dao.RatesDao
import com.example.revolutexercise.data.local.entity.Rate

@Database(entities = [Rate::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ratesDao(): RatesDao

}