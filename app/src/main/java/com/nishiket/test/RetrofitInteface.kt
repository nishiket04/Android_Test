package com.nishiket.test

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitInteface {
    @FormUrlEncoded
    @POST("/send-otp")
    fun sendOtp(@Field("contact_number") number: String): Call<Success>
}