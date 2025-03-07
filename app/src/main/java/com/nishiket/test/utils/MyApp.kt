package com.nishiket.test.utils

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp: Application() {
    private val BASE_URL = "https://strengthen-numbers-stag.dev-imaginovation.net/api/"
    companion object{
        @Volatile
        var RETROFIT_INSTANCE: Retrofit? = null
    }

    override fun onCreate() {
        super.onCreate()
        RETROFIT_INSTANCE = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}