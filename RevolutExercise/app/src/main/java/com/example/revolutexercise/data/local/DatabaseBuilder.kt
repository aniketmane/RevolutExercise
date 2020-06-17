package com.example.revolutexercise.data.local

import android.content.Context
import androidx.room.Room
import com.example.revolutexercise.data.local.constants.DatabaseConstants

object DatabaseBuilder {

    private var appDatabase: AppDatabase? = null

    var unitTest: Boolean = false
    fun getInstance(context: Context): AppDatabase {
        if (appDatabase == null) {
            synchronized(AppDatabase::class) {
                appDatabase = buildDatabase(context)
            }
        }
        return appDatabase!!
    }

    private fun buildDatabase(context: Context) =
        when {
            unitTest ->
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DatabaseConstants.DATABASE_NAME
                ).allowMainThreadQueries().build()
            else ->
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DatabaseConstants.DATABASE_NAME
                ).build()
        }

}