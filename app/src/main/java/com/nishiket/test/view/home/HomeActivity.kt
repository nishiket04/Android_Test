package com.nishiket.test.view.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.nishiket.test.R
import com.nishiket.test.databinding.ActivityHomeBinding
import com.nishiket.test.model.Data
import com.nishiket.test.model.EditProfileResponseModel
import com.nishiket.test.view.login.LoginActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var activityHomeBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(activityHomeBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sp = getSharedPreferences("login_state", MODE_PRIVATE)
        sp.edit().putBoolean("isLogIn",true).apply()
        val intent = intent
        val arguments: Parcelable? = when (val parcelableData = intent.extras?.getParcelable<Parcelable>("data")) {
            is Data -> parcelableData as Data
            is EditProfileResponseModel -> parcelableData as EditProfileResponseModel
            else -> null
        }
        val auth:String = intent?.extras?.getString("auth","").toString()
        arguments?.let { arg ->
            when (arg) {
                is Data -> {
                    Glide.with(this)
                        .load(arg.profile_photo)
                        .placeholder(R.mipmap.user_image_placeholder)
                        .into(activityHomeBinding.circleImageView)
                    sp.edit().putString("profile_photo",arg.profile_photo).apply()
                    sp.edit().putString("total_followers", arg.total_followers.toString()).apply()
                    sp.edit().putString("total_followings", arg.total_followings.toString()).apply()
                    sp.edit().putString("txtUserName",arg?.name).apply()
                    sp.edit().putString("txtUserBio",arg?.bio).apply()
                    sp.edit().putString("txtInterest",arg?.interests?.get(0) ?: "").apply()
                    sp.edit().putString("txtInterest2",arg?.interests?.get(1) ?: "").apply()
                    sp.edit().putString("txtInterest3",arg?.interests?.get(2) ?: "").apply()
                }
                is EditProfileResponseModel -> {
                    Glide.with(this)
                        .load(arg.profilePhoto) // Assuming profilePhoto is inside `data`
                        .placeholder(R.mipmap.user_image_placeholder)
                        .into(activityHomeBinding.circleImageView)
                    sp.edit().putString("profile_photo",arg.profilePhoto).apply()
                    sp.edit().putString("total_followers", arg.totalFollowers.toString()).apply()
                    sp.edit().putString("total_followings", arg.totalFollowings.toString()).apply()
                    sp.edit().putString("txtUserName",arg?.name).apply()
                    sp.edit().putString("txtUserBio",arg?.bio).apply()
                    sp.edit().putString("txtInterest",arg?.interests?.get(0) ?: "").apply()
                    sp.edit().putString("txtInterest2",arg?.interests?.get(1) ?: "").apply()
                    sp.edit().putString("txtInterest3",arg?.interests?.get(2) ?: "").apply()
                }
            }
        }
        Glide.with(this)
            .load(sp.getString("profile_photo",""))
            .placeholder(R.mipmap.user_image_placeholder)
            .into(activityHomeBinding.circleImageView)
        activityHomeBinding.txtFollowers.text = sp.getString("total_followers","")
        activityHomeBinding.txtFollowing.text = sp.getString("total_followings","")
        activityHomeBinding.txtUserName.text = sp.getString("txtUserName","")
        activityHomeBinding.txtUserBio.text = sp.getString("txtUserBio","")
        activityHomeBinding.txtInterest.text = sp.getString("txtInterest","")
        activityHomeBinding.txtInterest2.text = sp.getString("txtInterest2","")
        activityHomeBinding.txtInterest3.text = sp.getString("txtInterest3","")

        activityHomeBinding.btnLogout.setOnClickListener {
            sp.edit().clear().apply()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}