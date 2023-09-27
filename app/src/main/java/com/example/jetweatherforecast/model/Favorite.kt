package com.example.jetweatherforecast.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_tbl")
data class Favorite(
    @NonNull
    @PrimaryKey
    @ColumnInfo()
    val city: String,

    @ColumnInfo()
    val country: String
)
