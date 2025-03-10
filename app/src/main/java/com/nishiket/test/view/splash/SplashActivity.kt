package com.nishiket.test.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nishiket.test.R
import com.nishiket.test.view.home.HomeActivity
import com.nishiket.test.view.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sp = getSharedPreferences("login_state", MODE_PRIVATE)
        val isLogin = sp.getBoolean("isLogIn", false)
        Handler(Looper.getMainLooper()).postDelayed({
            if (isLogin) {
                val homeIntent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(homeIntent)
            } else {
                val loginIntent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            finish()
        }, 3000)
    }
}