package com.example.revolutexercise.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.revolutexercise.data.local.constants.DatabaseConstants
import com.example.revolutexercise.data.local.dao.RatesDao
import com.example.revolutexercise.data.local.entity.Rate


@Database(entities = [Rate::class], version = 1, exportSchema = false)
abstract class RevolutDatabase : RoomDatabase() {

    abstract val revolutDatabaseDao: RatesDao

    companion object {

        private const val DATABASE_NAME = DatabaseConstants.DATABASE_NAME

        @Volatile
        private var revolutDatabase: RevolutDatabase? = null

        fun getInstance(context: Context): RevolutDatabase {
            synchronized(this) {
                var instance =
                    revolutDatabase
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RevolutDatabase::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                    revolutDatabase = instance
                }

                return instance
            }
        }
    }
}