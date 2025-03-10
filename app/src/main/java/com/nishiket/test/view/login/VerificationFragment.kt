package com.nishiket.test.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nishiket.test.R
import com.nishiket.test.databinding.FragmentVerificationBinding
import com.nishiket.test.view.editprofile.EditProfileActivity
import com.nishiket.test.view.home.HomeActivity
import com.nishiket.test.viewmodel.UserDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log


class VerificationFragment : Fragment() {
    private lateinit var fragmentVerificationBinding: FragmentVerificationBinding
    private lateinit var viewModel: UserDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentVerificationBinding =
            FragmentVerificationBinding.inflate(inflater, container, false)
        return fragmentVerificationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val a = arguments?.getString("number", "")
        Log.d("TAG", "onViewCreated: $a")
        viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
        viewModel.liveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it.meta.message, Toast.LENGTH_LONG).show()
            if (viewModel.isSuccess) {
                Log.d("TAG", "onViewCreated: bshdbjs")
                val bundle = Bundle().apply {
                    putParcelable("data", it.data)
                    putString("auth",viewModel.auth)
                }
                startActivity(Intent(context, EditProfileActivity::class.java).putExtras(bundle))
                activity?.finish()
                viewModel.resetSuccess()
            }
        })

        fragmentVerificationBinding.edtOptDigit1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    fragmentVerificationBinding.edtOptDigit2.requestFocus()
                }
            }
        })

        fragmentVerificationBinding.edtOptDigit2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 0) {
                    fragmentVerificationBinding.edtOptDigit1.requestFocus()
                } else if (editable.length == 1) {
                    fragmentVerificationBinding.edtOptDigit3.requestFocus()
                }
            }
        })
        fragmentVerificationBinding.edtOptDigit3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 0) {
                    fragmentVerificationBinding.edtOptDigit2.requestFocus()
                } else if (editable.length == 1) {
                    fragmentVerificationBinding.edtOptDigit4.requestFocus()
                }
            }
        })
        fragmentVerificationBinding.edtOptDigit4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 0) {
                    fragmentVerificationBinding.edtOptDigit3.requestFocus()
                }
            }
        })

        val number = arguments?.getString("number", "") ?: ""

        fragmentVerificationBinding.txtVerificationDescription.text =
            "We have sent the verification code to your ${number}  mobile number."

        fragmentVerificationBinding.btnVerify.setOnClickListener {
            val otp = fragmentVerificationBinding.edtOptDigit1.getText()
                .toString() + fragmentVerificationBinding.edtOptDigit2.getText()
                .toString() + fragmentVerificationBinding.edtOptDigit3.getText()
                .toString() + fragmentVerificationBinding.edtOptDigit4.getText().toString()
            viewModel.verifyOtp(number, otp)
        }

        fragmentVerificationBinding.txtResendOtp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, LoginFragment())
                .commit()
        }

        CoroutineScope(Dispatchers.Default).launch {
            for(i in 25 downTo 1){
                withContext(Dispatchers.Main){
                    fragmentVerificationBinding.txtSec.text = i.toString()
                    delay(999)
                }
            }
        }
    }
}