package com.nishiket.test.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.nishiket.test.R
import com.nishiket.test.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var edt_number: EditText
    private lateinit var btn_login: Button
    private lateinit var viewModel: LoginViewModel
    private var number: String = ""
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
        viewModel.liveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it.meta.message, Toast.LENGTH_LONG).show()
            if (viewModel.isSuccess) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, VerificationFragment().apply { arguments = bundleOf("number" to "+1$number") })
                    .addToBackStack(null).commit()
                viewModel.resetSuccess()
            }
        })
        btn_login.setOnClickListener {
            number = edt_number.text.toString()
            if (number.isNotEmpty()) {
                viewModel.sendOtp("+1" + number)
            }
        }
    }

    private fun findId(view: View) {
        edt_number = view.findViewById(R.id.edt_number)
        btn_login = view.findViewById(R.id.btn_login)
    }
}