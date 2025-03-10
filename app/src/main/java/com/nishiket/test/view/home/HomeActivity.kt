package com.nishiket.test.view.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.nishiket.test.R
import com.nishiket.test.databinding.ActivityHomeBinding
import com.nishiket.test.model.Data
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
        val arguments : Data? = intent.extras?.getParcelable("data")

        Glide.with(this).load(arguments?.profile_photo).placeholder(R.mipmap.user_image_placeholder).into(activityHomeBinding.circleImageView)
        sp.edit().putString("total_followers",arguments?.total_followers.toString()).apply()
        sp.edit().putString("total_followings",arguments?.total_followings.toString()).apply()
        sp.edit().putString("txtUserName",arguments?.name).apply()
        sp.edit().putString("txtUserBio",arguments?.bio).apply()
        sp.edit().putString("txtInterest",arguments?.interests?.get(0) ?: "").apply()
        sp.edit().putString("txtInterest2",arguments?.interests?.get(1) ?: "").apply()
        sp.edit().putString("txtInterest3",arguments?.interests?.get(2) ?: "").apply()

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