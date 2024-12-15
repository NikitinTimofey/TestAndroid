package com.example.registrationpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class SellActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        val editTextBrand: TextView = findViewById(R.id.edit_text_brand)
    }
}