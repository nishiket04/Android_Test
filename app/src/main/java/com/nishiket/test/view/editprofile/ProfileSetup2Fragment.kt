package com.nishiket.test.view.editprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nishiket.test.R
import com.nishiket.test.databinding.FragmentProfileSetup2Binding
import com.nishiket.test.model.Data
import com.nishiket.test.viewmodel.EditProfileViewModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileSetup2Fragment : Fragment() {
    private lateinit var fragmentProfileSetup2Binding: FragmentProfileSetup2Binding
    private var body: MultipartBody.Part? = null
    private lateinit var editProfile: EditProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProfileSetup2Binding =
            FragmentProfileSetup2Binding.inflate(inflater, container, false)
        return fragmentProfileSetup2Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editProfile = ViewModelProvider(this)[EditProfileViewModel::class.java]
        fragmentProfileSetup2Binding.edtGender.isFocusable = false
        fragmentProfileSetup2Binding.edtGender.setOnClickListener {
            val genderItems = arrayOf("Male", "Female")
            val checkedItem = 0

            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("Gender")
                    .setNeutralButton("Cancel") { dialog, which ->
                        dialog.cancel()
                    }
                    .setPositiveButton("Select") { dialog, which ->
                        dialog.cancel()
                    }.setCancelable(false)
                    .setSingleChoiceItems(genderItems, checkedItem) { dialog, which ->
                        fragmentProfileSetup2Binding.edtGender.setText(genderItems[which])
                    }
                    .show()
            }
        }

        editProfile.liveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it.meta.message, Toast.LENGTH_LONG).show()
            if (editProfile.isSuccess) {
                (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.visibility = View.VISIBLE
                (activity as EditProfileActivity).activityEditProfileBinding.pbLoader.visibility = View.GONE
                (activity as EditProfileActivity).activityEditProfileBinding.viewPager.currentItem =
                    2
                editProfile.resetSuccess()
            }
        })

        fragmentProfileSetup2Binding.ivAddPhoto.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                i.setType("image/*")
                startActivityForResult(i, 101)
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 50)
            }
        }
        val argument = arguments
        fragmentProfileSetup2Binding.edtUserName.setText(argument?.getParcelable<Data>("data")?.username)
        fragmentProfileSetup2Binding.edtBio.setText(argument?.getParcelable<Data>("data")?.bio)
        fragmentProfileSetup2Binding.edtGender.setText(argument?.getParcelable<Data>("data")?.gender)
        Glide.with(requireContext()).load(argument?.getParcelable<Data>("data")?.profile_photo)
            .circleCrop().into(fragmentProfileSetup2Binding.ivAddPhoto)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 50 && grantResults.size > 0) {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            i.setType("image/*")
            startActivityForResult(i, 101)
        }
    }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(contentUri, proj, null, null, null)
            ?: return null
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result
    }

    private fun uriToFile(uri: Uri): File {
        val filePath: String? = getRealPathFromURI(uri)
        return File(filePath!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK && data != null) {
            val image = data.data
            if (image != null) {
                Glide.with(requireContext()).load(Uri.parse(image.toString())).circleCrop()
                    .into(fragmentProfileSetup2Binding.ivAddPhoto)
                val imageFile: File = uriToFile(image)
                val requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile)
                body = MultipartBody.Part.createFormData("profile_photo", imageFile.name, requestFile)
            }
        }
        Log.d(
            "ActivityResult",
            "requestCode: " + requestCode + ", resultCode: " + resultCode
        )
    }

    override fun onResume() {
        super.onResume()
        (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.setOnClickListener(null)

        (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.setOnClickListener {
            val userName = fragmentProfileSetup2Binding.edtUserName.text.toString()
            val bio = fragmentProfileSetup2Binding.edtBio.text.toString()
            val gender = fragmentProfileSetup2Binding.edtGender.text.toString().lowercase()

            if (userName.isEmpty() || bio.isEmpty() || gender.isEmpty() || body == null) {
                Toast.makeText(context, "Enter all the fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val argument = arguments
            if (argument != null) {
                (activity as EditProfileActivity).activityEditProfileBinding.btnLogin.visibility = View.GONE
                (activity as EditProfileActivity).activityEditProfileBinding.pbLoader.visibility = View.VISIBLE
                body?.let { imagePart ->
                    editProfile.editProfileImage(
                        argument.getString("auth", ""), userName, gender, bio, imagePart
                    )
                }
            }
        }
    }
}