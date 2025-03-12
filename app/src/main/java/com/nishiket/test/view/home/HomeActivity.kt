package com.nishiket.test.view.home

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nishiket.test.R
import com.nishiket.test.adapter.PostAdapter
import com.nishiket.test.databinding.ActivityHomeBinding
import com.nishiket.test.model.Data
import com.nishiket.test.model.EditProfileResponseModel
import com.nishiket.test.model.FeedData
import com.nishiket.test.viewmodel.FeedViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var feedViewModel: FeedViewModel
    private val posts: MutableList<FeedData> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        feedViewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val sp = getSharedPreferences("login_state", MODE_PRIVATE)
        sp.edit().putBoolean("isLogIn", true).apply()
        val intent = intent
        val argument: Parcelable? =
            when (val parcelableData = intent.extras?.getParcelable<Parcelable>("data")) {
                is Data -> parcelableData as Data
                is EditProfileResponseModel -> parcelableData as EditProfileResponseModel
                else -> null
            }
        val bundle = Bundle().apply {
            putParcelable("data", argument)
            putString("auth", sp.getString("auth", ""))

        }
        sp.getString("auth", "")?.let { feedViewModel.getFeed(it) }
        feedViewModel.liveData.observe(this, {
            Toast.makeText(this, it.meta?.message, Toast.LENGTH_LONG).show()
            Log.d("TAG", "onCreate: ${it.data}")
            val adapter = PostAdapter(it.data)
            activityHomeBinding.rvPost.adapter = adapter
            activityHomeBinding.rvPost.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL, false
            )
        })
        argument?.let { arg ->
            when (arg) {
                is Data -> {
                    sp.edit().putString("profile_photo", arg.profile_photo).apply()
                    sp.edit().putString("total_followers", arg.total_followers.toString()).apply()
                    sp.edit().putString("total_followings", arg.total_followings.toString()).apply()
                    sp.edit().putString("txtUserName", arg?.name).apply()
                    sp.edit().putString("txtUserBio", arg?.bio).apply()
                    sp.edit().putString("txtInterest", arg?.interests?.get(0) ?: "").apply()
                    sp.edit().putString("txtInterest2", arg?.interests?.get(1) ?: "").apply()
                    sp.edit().putString("txtInterest3", arg?.interests?.get(2) ?: "").apply()
                }

                is EditProfileResponseModel -> {
                    sp.edit().putString("profile_photo", arg.profilePhoto).apply()
                    sp.edit().putString("total_followers", arg.totalFollowers.toString()).apply()
                    sp.edit().putString("total_followings", arg.totalFollowings.toString()).apply()
                    sp.edit().putString("txtUserName", arg?.name).apply()
                    sp.edit().putString("txtUserBio", arg?.bio).apply()
                    sp.edit().putString("txtInterest", arg?.interests?.get(0) ?: "").apply()
                    sp.edit().putString("txtInterest2", arg?.interests?.get(1) ?: "").apply()
                    sp.edit().putString("txtInterest3", arg?.interests?.get(2) ?: "").apply()
                }
            }
//            activityHomeBinding.rvPost.adapter = adapter
//            activityHomeBinding.rvPost.layoutManager = LinearLayoutManager(
//                this,
//                LinearLayoutManager.VERTICAL, true
//            )


//            supportFragmentManager.beginTransaction().replace(
//                activityHomeBinding.homeFragmentContainer.id,
//                HomeFragment().apply { arguments = bundle }).commit()
//
//            activityHomeBinding.bottomNavigationView.setOnItemSelectedListener {
//                when (it.itemId) {
//                    R.id.home -> {
//                        supportFragmentManager.beginTransaction().add(
//                            activityHomeBinding.homeFragmentContainer.id,
//                            HomeFragment().apply { arguments = bundle }).commit()
//                        true
//                    }
//
//                    R.id.nav_element -> {
//                        true
//                    }
//
//                    R.id.nav_element2 -> {
//                        true
//                    }
//
//                    R.id.user -> {
//                        supportFragmentManager.beginTransaction().add(
//                            activityHomeBinding.homeFragmentContainer.id,
//                            ProfileFragment().apply { arguments = bundle }).commit()
//                        true
//                    }
//
//                    else -> {
//                        false
//                    }
//                }
//            }
        }
    }
}