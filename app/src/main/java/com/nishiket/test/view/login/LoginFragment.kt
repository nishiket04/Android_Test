package com.nishiket.test.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nishiket.test.R
import com.nishiket.test.RetrofitInteface
import com.nishiket.test.utils.MyApp
import com.nishiket.test.viewmodel.LoginViewModel
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.create

class LoginFragment : Fragment() {
    private lateinit var edt_number :EditText
    private lateinit var btn_login :Button
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findId(view)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        btn_login.setOnClickListener {
            val number = edt_number.text.toString()
            if(number.isNotEmpty()){
                viewModel.sendOtp(number)
            }
        }
    }

    private fun findId(view: View){
        edt_number = view.findViewById(R.id.edt_number)
        btn_login = view.findViewById(R.id.btn_login)
    }
}