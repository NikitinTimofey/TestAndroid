package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.registrationpage.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.buttonAuth.setOnClickListener {
            val email = binding.userEmailAuth.text.toString().trim()
            val password = binding.userPassAuth.text.toString().trim()

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "Вход выполнен!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ItemsActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Ошибка: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        binding.linkToReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
