package com.nishiket.test.utils.network

import com.nishiket.test.model.EditProfile
import com.nishiket.test.model.EditProfileRequest
import com.nishiket.test.model.FeedModel
import com.nishiket.test.model.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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


    @FormUrlEncoded
    @POST("v2/edit-profile")
    suspend fun editProfile(
        @Header("Authorization") auth:String,
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("dob") dob:String
    ): Response<EditProfile>

    @FormUrlEncoded
    @POST("v2/edit-profile")
    suspend fun editProfile1(
        @Header("Authorization") auth:String,
        @Field("username") username:String,
        @Field("gender") gender:String,
        @Field("bio") bio:String,
    ): Response<EditProfile>

    @Multipart
    @POST("v2/edit-profile")
    suspend fun editProfileImage(
        @Header("Authorization") auth:String,
        @Part profile_photo:MultipartBody.Part
    ): Response<EditProfile>

    @POST("v2/edit-profile")
    suspend fun editProfileInterest(
        @Header("Authorization") auth:String,
        @Body requestBody: EditProfileRequest
    ): Response<EditProfile>

    @GET("v2/get-all-feeds")
    suspend fun getFeed(
        @Header("Authorization") auth:String,
    ): Response<FeedModel>

}