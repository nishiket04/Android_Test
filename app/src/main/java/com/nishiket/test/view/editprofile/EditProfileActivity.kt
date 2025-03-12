package com.nishiket.test.view.editprofile

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nishiket.test.R
import com.nishiket.test.adapter.EditProfileViewPagerAdapter
import com.nishiket.test.databinding.ActivityEditProfileBinding
import com.nishiket.test.model.Data

class EditProfileActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var activityEditProfileBinding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityEditProfileBinding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(activityEditProfileBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = intent
        val data: Data? = intent.extras?.getParcelable("data")
        val auth = intent.extras?.getString("auth")
        val bundle = Bundle().apply {
            putParcelable("data", data)
            putString("auth", auth)
        }

        activityEditProfileBinding.viewPager.isUserInputEnabled = false

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED -> {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 100
                )
            }

            else -> {
                findLocation()
            }
        }

        activityEditProfileBinding.txtPrv.setOnClickListener {
            activityEditProfileBinding.viewPager.currentItem -= 1
        }

        activityEditProfileBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    activityEditProfileBinding.btnLogin.text = "Submit"
                    activityEditProfileBinding.txtPrv.visibility = View.VISIBLE
                } else {
                    activityEditProfileBinding.btnLogin.text = "Next"
                    activityEditProfileBinding.txtPrv.visibility = View.VISIBLE
                }
            }
        })

        val fragments = listOf(ProfileSetup1Fragment().apply { arguments = bundle },
            ProfileSetup2Fragment().apply { arguments = bundle },
            ProfileSetup3Fragment().apply { arguments = bundle })

        val viewPagerAdapter =
            EditProfileViewPagerAdapter(supportFragmentManager, lifecycle, fragments)
        activityEditProfileBinding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(
            activityEditProfileBinding.tabLayout,
            activityEditProfileBinding.viewPager
        )
        { tab, position ->
            tab.text = ""
        }.attach()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty()) {
            findLocation()
        }
    }

    private fun findLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                val sp = getSharedPreferences("user_location", MODE_PRIVATE)
                location?.latitude?.toLong()?.let { sp.edit().putLong("lat", it).apply() }
                location?.longitude?.toLong()?.let { sp.edit().putLong("long", it).apply() }
                Log.d("TAG", "findLocation: ${location?.latitude} ${location?.longitude} ")
            }
    }
}