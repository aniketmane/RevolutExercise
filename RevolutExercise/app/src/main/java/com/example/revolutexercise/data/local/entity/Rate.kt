package com.example.revolutexercise.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.revolutexercise.data.local.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_NAME)
data class Rate(
    @PrimaryKey(autoGenerate = true)
    val rateId: Long = 0L,
    @ColumnInfo(name = DatabaseConstants.COLUMN_CURRENCY_NAME)
    val code: String,
    @ColumnInfo(name = DatabaseConstants.COLUMN_CURRENCY_RATE)
    var rate: Double,
    @ColumnInfo(name = DatabaseConstants.COLUMN_FLAG_IMAGE_ID)
    val flagImageResId: Int
)
