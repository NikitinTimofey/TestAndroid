package com.example.registrationpage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationpage.databinding.ActivityItemBinding
import com.google.firebase.firestore.FirebaseFirestore

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем documentId из Intent
        val documentId = intent.getStringExtra("itemDocumentId") ?: ""

        if (documentId.isNotEmpty()) {
            loadItemData(documentId)
        } else {
            Toast.makeText(this, "Ошибка: documentId отсутствует", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadItemData(documentId: String) {
        // Загружаем данные элемента из Firestore
        firestore.collection("Items").document(documentId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Отображаем данные об автомобиле
                    val brand = document.getString("brand") ?: "Unknown"
                    val text = document.getString("text") ?: "No description"
                    val price = document.getLong("price")?.toInt() ?: 0
                    val year = document.getLong("year")?.toInt() ?: 0
                    val transmission = document.getString("transmission") ?: "Unknown"
                    val engineSize = document.getDouble("engineSize")?.toFloat() ?: 0f
                    val fuel = document.getString("fuel") ?: "Unknown"
                    val body = document.getString("body") ?: "Unknown"
                    val mileage = document.getLong("mileage")?.toInt() ?: 0
                    val imageUrl = document.getString("imageUrl") ?: ""
                    val userId = document.getString("userId") ?: ""

                    // Отображаем данные в интерфейсе
                    binding.itemTitle.text = brand
                    binding.itemDescription.text = text
                    binding.itemPrice.text = "$price $"
                    binding.itemYear.text = "Год выпуска: $year"
                    binding.itemTransmission.text = "Коробка передач: $transmission"
                    binding.itemEngineSize.text = "Объем двигателя: $engineSize л"
                    binding.itemFuel.text = "Тип топлива: $fuel"
                    binding.itemBody.text = "Кузов: $body"
                    binding.itemMileage.text = "Пробег: $mileage км"

                    // Загружаем изображение (если используется base64)
                    if (imageUrl.isNotEmpty()) {
                        val decodedBytes = android.util.Base64.decode(imageUrl, android.util.Base64.DEFAULT)
                        val bitmap = android.graphics.BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        binding.itemImage.setImageBitmap(bitmap)
                    } else {
                        binding.itemImage.setImageResource(R.drawable.placeholder) // Заглушка
                    }

                } else {
                    Toast.makeText(this, "Объявление не найдено", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Ошибка загрузки объявления: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
