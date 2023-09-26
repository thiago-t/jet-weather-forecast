package com.example.jetweatherforecast.data

class DataOrException<T, Boolean, E : Exception>(
    var data: T? = null,
    var isLoading: kotlin.Boolean? = null,
    var e: E? = null
)