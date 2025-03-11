package com.nishiket.test.view.editprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nishiket.test.R
import com.nishiket.test.adapter.InterestAdapter
import com.nishiket.test.databinding.FragmentProfileSetup3Binding
import com.nishiket.test.model.Data
import com.nishiket.test.model.EditProfileRequest
import com.nishiket.test.model.InterestModel
import com.nishiket.test.view.home.HomeActivity
import com.nishiket.test.viewmodel.EditProfileViewModel

class ProfileSetup3Fragment : Fragment() {
    private lateinit var fragmentProfileSetup3Binding: FragmentProfileSetup3Binding
    private lateinit var updatedList:MutableList<InterestModel>
    private lateinit var editProfile: EditProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProfileSetup3Binding =
            FragmentProfileSetup3Binding.inflate(inflater, container, false)
        return fragmentProfileSetup3Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editProfile = ViewModelProvider(this)[EditProfileViewModel::class.java]
        fragmentProfileSetup3Binding.edtFitnessLevel.isFocusable = false
        fragmentProfileSetup3Binding.edtFitnessLevel.setOnClickListener {
            val genderItems = arrayOf("Beginner", "Intermediate", "Advanced")
            val checkedItem = 0

            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("Fitness Level")
                    .setNeutralButton("Cancel") { dialog, which ->
                        dialog.cancel()
                    }
                    .setPositiveButton("Select") { dialog, which ->
                        dialog.cancel()
                    }.setCancelable(false)
                    .setSingleChoiceItems(genderItems, checkedItem) { dialog, which ->
                        fragmentProfileSetup3Binding.edtFitnessLevel.setText(genderItems[which])
                    }
                    .show()
            }
        }


        val argument = arguments
        editProfile.liveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it.meta.message, Toast.LENGTH_LONG).show()
            if (editProfile.isSuccess) {
                Log.d("TAG", "onViewCreated: bshdbjs")
                (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.visibility = View.VISIBLE
                (activity as EditProfileActivity).activityEditProfileBinding.pbLoader.visibility = View.GONE
                val bundle = Bundle().apply {
                    putParcelable("data", it.data)
                    putString("auth",argument?.getString("auth", ""))
                }
                startActivity(Intent(context, HomeActivity::class.java).putExtras(bundle))
                activity?.finish()
                editProfile.resetSuccess()
            }
        })

        fragmentProfileSetup3Binding.edtFitnessLevel.setText(argument?.getParcelable<Data>("data")?.fitness_level)
        val selectedInterests = argument?.getParcelable<Data>("data")?.interests ?: emptyList()

        val allInterests = listOf(
            InterestModel("Gym", R.drawable.gym),
            InterestModel("Yoga", R.drawable.yoga),
            InterestModel("Cardio", R.drawable.cardio),
            InterestModel("Workout", R.drawable.home_workout),
            InterestModel("Cycling", R.drawable.cycling),
            InterestModel("Zumba", R.drawable.zumba),
            InterestModel("Dieting", R.drawable.diet),
            InterestModel("Sports", R.drawable.sports),
            InterestModel("Running", R.drawable.running)
        )

        updatedList = allInterests.map { interest ->
            interest.copy(isSelected = selectedInterests.contains(interest.interest))
        }.toMutableList()

        val adapter = InterestAdapter(updatedList)
        fragmentProfileSetup3Binding.gvIntrest.adapter = adapter
        fragmentProfileSetup3Binding.gvIntrest.layoutManager =
            GridLayoutManager(requireContext(), 3)
    }

    override fun onResume() {
        super.onResume()
        (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.setOnClickListener(null)

        (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.setOnClickListener {
            val fitnessLevel = fragmentProfileSetup3Binding.edtFitnessLevel.text.toString().lowercase().trim()
            val selectedInterestNames = ArrayList(updatedList.filter { it.isSelected }.map { it.interest })

            if (fitnessLevel.isEmpty() || selectedInterestNames.size < 3) {
                Toast.makeText(context, "Enter all the fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val argument = arguments
            if (argument != null) {
                val requestBody = EditProfileRequest(
                    fitness_level = fitnessLevel,
                    interests = selectedInterestNames
                )
                (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.visibility = View.GONE
                (activity as EditProfileActivity).activityEditProfileBinding.pbLoader.visibility = View.VISIBLE
                editProfile.editProfileInterest(argument.getString("auth", ""),requestBody)
            }
        }
    }
}