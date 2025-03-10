package com.nishiket.test.utils.network

import com.nishiket.test.model.Data
import com.nishiket.test.model.EditProfile
import com.nishiket.test.model.EditProfileModel
import com.nishiket.test.model.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitInterface {
    @FormUrlEncoded
    @POST("v2/send-otp")
    suspend fun sendOtp(@Field("contact_number") number: String): Response<LoginResponse>

    @FormUrlEncoded
    @POST("v2/verify-otp")
    suspend fun verifyOtp(
        @Field("contact_number") number: String,
        @Field("otp") otp: Int
    ): Response<LoginResponse>


    @POST("v2/edit-profile")
    suspend fun editProfile(
        @Header("Authorization") auth:String,
        @Body data:EditProfileModel
    ): Response<EditProfile>
}