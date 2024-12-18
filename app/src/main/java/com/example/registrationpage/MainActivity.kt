package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationpage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainLinkToReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.mainLinkToAuth.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }
}