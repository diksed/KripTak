package com.sedatkavak.kriptak.screens.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.sedatkavak.kriptak.MainActivity
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.databinding.ActivitySplashBinding
import com.sedatkavak.kriptak.screens.home_fragment.HomeFragment

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val frontLogo: ImageView = binding.logoImageView
        val imgStartAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        frontLogo.startAnimation(imgStartAnim)
        Handler().postDelayed(Runnable {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
            finish()
        }, 1700)
    }
}