package com.nishiket.test.viewmodel

import androidx.lifecycle.ViewModel
import com.nishiket.test.RetrofitInteface
import com.nishiket.test.utils.MyApp
import retrofit2.Callback

class LoginViewModel : ViewModel() {
    private val retrofit: RetrofitInteface? = MyApp.RETROFIT_INSTANCE?.create(RetrofitInteface::class.java)

    fun sendOtp(number : String){

        retrofit?.sendOtp(number)?.enqueue(object : Callback<Success>)
    }
}