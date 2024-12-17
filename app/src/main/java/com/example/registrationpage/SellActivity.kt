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
            val brand = binding.spinnerBrand.selectedItem.toString()
            val fuel = binding.spinnerFuel.selectedItem.toString()
            val body = binding.spinnerBody.selectedItem.toString()
            val engineSize = binding.spinnerEngineSize.selectedItem.toString().toFloatOrNull()
            val transmission = binding.spinnerTransmission.selectedItem.toString()
            val year = binding.editYear.text.toString().toIntOrNull()
            val price = binding.editTextPrice.text.toString().toIntOrNull()
            val mileage = binding.editMileage.text.toString().toIntOrNull()
            val description  = binding.editDiscription.text.toString()

            val textFields = listOf(brand, description, transmission, fuel, body)
            val numFields = listOf(year, price, engineSize, mileage) //

            if (textFields.all { it.isNotEmpty() } && numFields.all { it != null && it != 0 }) {
                // Создаем объект для Firestore
                val newItem = hashMapOf(
                    "brand" to brand,
                    "year" to year,
                    "price" to price,
                    "description" to description,
                    "transmission" to transmission,
                    "engineSize" to engineSize,
                    "fuel" to fuel,
                    "body" to body,
                    "mileage" to mileage
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
