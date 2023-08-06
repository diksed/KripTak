package com.sedatkavak.kriptak.screens.connection_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sedatkavak.kriptak.databinding.ActivityConnectionBinding
import com.sedatkavak.kriptak.screens.splash_screen.SplashActivity

class ConnectionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityConnectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tryAgainButton.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}
