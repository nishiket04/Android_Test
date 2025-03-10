package com.nishiket.test.view.editprofile

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.nishiket.test.R
import com.nishiket.test.databinding.FragmentProfileSetup1Binding
import com.nishiket.test.model.Data
import com.nishiket.test.model.EditProfileModel
import com.nishiket.test.view.login.VerificationFragment
import com.nishiket.test.viewmodel.EditProfileViewModel
import com.nishiket.test.viewmodel.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileSetup1Fragment : Fragment() {
    private lateinit var fragmentProfileSetup1Binding: FragmentProfileSetup1Binding
    private val calendar = Calendar.getInstance()
    private lateinit var editProfile: EditProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProfileSetup1Binding =
            FragmentProfileSetup1Binding.inflate(inflater, container, false)
        return fragmentProfileSetup1Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editProfile = ViewModelProvider(this)[EditProfileViewModel::class.java]
        fragmentProfileSetup1Binding.edtDob.isFocusable = false
        fragmentProfileSetup1Binding.edtDob.setOnClickListener {
            showDatePicker()
        }
        val ar = arguments
        fragmentProfileSetup1Binding.btnLogin.setOnClickListener {
            val name = fragmentProfileSetup1Binding.edtName.text.toString()
            val email = fragmentProfileSetup1Binding.edtEmail.text.toString()
            val dob = fragmentProfileSetup1Binding.edtDob.text.toString()
            if (ar != null) {
                editProfile.editProfile(EditProfileModel(name = name, email = email, dob = dob),ar.getString("auth",""))
            }
        }

        editProfile.liveData.observe(viewLifecycleOwner, {
//            Toast.makeText(context, it.meta.message, Toast.LENGTH_LONG).show()
            if (editProfile.isSuccess) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, ProfileSetup2Fragment())
                    .addToBackStack(null).commit()
                editProfile.resetSuccess()
            }
        })
    }

    private fun showDatePicker() {
        val datePickerDialog = context?.let {
            DatePickerDialog(
                it, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    fragmentProfileSetup1Binding.edtDob.setText("$formattedDate")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        // Show the DatePicker dialog
        datePickerDialog?.show()
    }
}