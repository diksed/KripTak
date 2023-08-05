package com.sedatkavak.kriptak.screens.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.sedatkavak.kriptak.MainActivity
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionActivity
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionUtils

class SplashActivity : AppCompatActivity() {
    private val splashDelay: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieSplashView)
        lottieAnimationView.setAnimation(R.raw.kriptak_splash)
        lottieAnimationView.playAnimation()


        Handler().postDelayed({
            checkInternetConnection()
        }, splashDelay)
    }
    private fun checkInternetConnection() {
        if (ConnectionUtils.isNetworkAvailable(this)) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this@SplashActivity, ConnectionActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}
