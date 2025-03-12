package com.nishiket.test.view.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.bumptech.glide.Glide
import com.nishiket.test.R
import com.nishiket.test.databinding.FragmentProfileBinding
import com.nishiket.test.model.Data
import com.nishiket.test.model.EditProfileResponseModel
import com.nishiket.test.view.login.LoginActivity

class ProfileFragment : Fragment() {
    private lateinit var fragmentProfileBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return fragmentProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sp = context?.getSharedPreferences("login_state", MODE_PRIVATE)

        Glide.with(this)
            .load(sp?.getString("profile_photo", ""))
            .placeholder(R.mipmap.user_image_placeholder)
            .into(fragmentProfileBinding.circleImageView)
        fragmentProfileBinding.txtFollowers.text = sp?.getString("total_followers", "")
        fragmentProfileBinding.txtFollowing.text = sp?.getString("total_followings", "")
        fragmentProfileBinding.txtUserName.text = sp?.getString("txtUserName", "")
        fragmentProfileBinding.txtUserBio.text = sp?.getString("txtUserBio", "")
        fragmentProfileBinding.txtInterest.text = sp?.getString("txtInterest", "")
        fragmentProfileBinding.txtInterest2.text = sp?.getString("txtInterest2", "")
        fragmentProfileBinding.txtInterest3.text = sp?.getString("txtInterest3", "")

        fragmentProfileBinding.btnLogout.setOnClickListener {
            sp?.edit()?.clear()?.apply()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}