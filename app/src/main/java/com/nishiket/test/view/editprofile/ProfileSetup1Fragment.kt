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
        (activity as EditProfileActivity).activityEditProfileBinding.pbLoader.visibility = View.GONE
        (activity as EditProfileActivity).activityEditProfileBinding.txtPrv.visibility = View.GONE
        fragmentProfileSetup1Binding.edtDob.isFocusable = false
        fragmentProfileSetup1Binding.edtDob.setOnClickListener {
            showDatePicker()
        }
        val argument = arguments

        fragmentProfileSetup1Binding.edtName.setText(argument?.getParcelable<Data>("data")?.name)
        fragmentProfileSetup1Binding.edtEmail.setText(argument?.getParcelable<Data>("data")?.email)
        fragmentProfileSetup1Binding.edtDob.setText(argument?.getParcelable<Data>("data")?.dob)


        editProfile.liveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it.meta.message, Toast.LENGTH_LONG).show()
            if (editProfile.isSuccess) {
                (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.visibility = View.VISIBLE
                (activity as EditProfileActivity).activityEditProfileBinding.pbLoader.visibility = View.GONE
                (activity as EditProfileActivity).activityEditProfileBinding.viewPager.currentItem = 1
                editProfile.resetSuccess()
            }
        })
    }

    private fun showDatePicker() {
        calendar.add(Calendar.YEAR, -18)
        val maxDate = calendar.timeInMillis
        val datePickerDialog = context?.let {
            DatePickerDialog(
                it, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    fragmentProfileSetup1Binding.edtDob.setText("$formattedDate")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        datePickerDialog?.datePicker?.maxDate = maxDate
        datePickerDialog?.show()
    }

    override fun onResume() {
        super.onResume()
        (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.setOnClickListener(null)

        (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.setOnClickListener {
            val name = fragmentProfileSetup1Binding.edtName.text.toString().trim()
            val email = fragmentProfileSetup1Binding.edtEmail.text.toString().trim()
            val dob = fragmentProfileSetup1Binding.edtDob.text.toString()

            if (name.isEmpty() || email.isEmpty() || dob.isEmpty() ||
                !fragmentProfileSetup1Binding.ckAge.isChecked ||
                !fragmentProfileSetup1Binding.ckTac.isChecked) {
                Toast.makeText(context, "Enter all the fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val argument = arguments
            if (argument != null) {
                (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.visibility = View.GONE
                (activity as EditProfileActivity).activityEditProfileBinding.pbLoader.visibility = View.VISIBLE
                editProfile.editProfile(name, email, dob, argument.getString("auth", ""))
            }
        }
    }
}