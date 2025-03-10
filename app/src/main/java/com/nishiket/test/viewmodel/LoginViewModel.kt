package com.nishiket.test.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nishiket.test.model.LoginResponse
import com.nishiket.test.utils.network.RetrofitInterface
import com.nishiket.test.utils.MyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    private val retrofit: RetrofitInterface? =
        MyApp.RETROFIT_INSTANCE?.create(RetrofitInterface::class.java)
    private var _isSuccess = false
    val isSuccess: Boolean
        get() {
            return _isSuccess
        }
    private val mutableLiveData = MutableLiveData<LoginResponse>()
    val liveData: LiveData<LoginResponse>
        get() {
            return mutableLiveData
        }

    fun resetSuccess() {
        _isSuccess = false
    }

    fun sendOtp(number: String) {
        Log.d("TAG", "odkdkdncjksdncjd555")
        CoroutineScope(Dispatchers.Default).launch {
            val response = retrofit?.sendOtp(number)
            response?.let {
                if (it.isSuccessful && it.code() == 200) {
                    _isSuccess = true
                    mutableLiveData.postValue(it.body())
                } else {
                    _isSuccess = false
                }
            }
        }
    }
}