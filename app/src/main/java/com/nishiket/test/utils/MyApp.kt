package com.nishiket.test.utils

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyApp: Application() {
    private val BASE_URL = "https://strengthen-numbers.dev-imaginovation.net/api/"
    companion object{
        @Volatile
        var RETROFIT_INSTANCE: Retrofit? = null
    }

    override fun onCreate() {
        super.onCreate()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        RETROFIT_INSTANCE = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}