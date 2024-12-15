package com.example.registrationpage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationpage.databinding.ActivitySellBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SellActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellBinding
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Обработка нажатия кнопки "Добавить"
        binding.btnAddItem.setOnClickListener {
            val brand = binding.editTextBrand.text.toString()
            val year = binding.editTextYear.text.toString().toIntOrNull() ?: 2000
            val price = binding.editTextPrice.text.toString().toIntOrNull() ?: 0
            val description = binding.editTextTransmission.text.toString()


            if (brand.isNotEmpty()) {
                // Создаем объект для Firestore
                val newItem = hashMapOf(
                    "brand" to brand,
                    "year" to year,
                    "price" to price,
                    "description" to description,
                    "transmission" to "manual", // Дополнительные поля
                    "engineSize" to 1.6,
                    "fuel" to "gas",
                    "body" to "sedan",
                    "mileage" to 0
                )

                // Добавляем в Firestore
                firestore.collection("Items")
                    .add(newItem)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Объявление добавлено!", Toast.LENGTH_SHORT).show()
                        finish() // Закрываем активити
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
