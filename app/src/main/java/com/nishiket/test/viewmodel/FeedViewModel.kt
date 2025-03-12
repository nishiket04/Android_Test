package com.nishiket.test.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nishiket.test.model.EditProfile
import com.nishiket.test.model.FeedModel
import com.nishiket.test.utils.MyApp
import com.nishiket.test.utils.network.RetrofitInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel() : ViewModel() {
    private val retrofit: RetrofitInterface? =
        MyApp.RETROFIT_INSTANCE?.create(RetrofitInterface::class.java)
    private val mutableLiveData = MutableLiveData<FeedModel>()
    val liveData: LiveData<FeedModel>
        get() {
            return mutableLiveData
        }

    fun getFeed(auth: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val response = retrofit?.getFeed("Bearer " + auth)
            response?.let {
                Log.d("TAG", "verifyOtp: ${it.code()}")
                if (it.isSuccessful && it.code() == 200) {
                    mutableLiveData.postValue(it.body())
                    Log.d("TAG", "editProfile:${it.code()} ${it.headers()} ${it.body()}")
                } else {
                    mutableLiveData.postValue(it.body())
                }
            }
        }
    }
}