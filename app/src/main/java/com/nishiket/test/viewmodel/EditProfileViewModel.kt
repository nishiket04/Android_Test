package com.nishiket.test.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nishiket.test.model.Data
import com.nishiket.test.model.EditProfile
import com.nishiket.test.model.EditProfileModel
import com.nishiket.test.model.LoginResponse
import com.nishiket.test.utils.MyApp
import com.nishiket.test.utils.network.RetrofitInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class EditProfileViewModel : ViewModel() {
    private val retrofit: RetrofitInterface? =
        MyApp.RETROFIT_INSTANCE?.create(RetrofitInterface::class.java)
    private var _isSuccess = false
    val isSuccess: Boolean
        get() {
            return _isSuccess
        }
    private val mutableLiveData = MutableLiveData<EditProfile>()
    val liveData: LiveData<EditProfile>
        get() {
            return mutableLiveData
        }

    fun resetSuccess() {
        _isSuccess = false
    }

    fun editProfile(data:EditProfileModel,auth:String) {
        CoroutineScope(Dispatchers.Default).launch {
            val response = retrofit?.editProfile(auth,data)
            response?.let {
                Log.d("TAG", "verifyOtp: ${it.code()}")
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