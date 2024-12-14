package com.example.registrationpage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SellActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        val dbHelper = DbHelper(this, null)

        val brandInput: EditText = findViewById(R.id.item_brand)
        val descInput: EditText = findViewById(R.id.item_description)
        val priceInput: EditText = findViewById(R.id.item_price)
        val saveButton: Button = findViewById(R.id.btn_save_item)

        saveButton.setOnClickListener {
            val brand = brandInput.text.toString()
            val desc = descInput.text.toString()
            val price = priceInput.text.toString().toDoubleOrNull()

            if (brand.isEmpty() || desc.isEmpty() || price == null) {
                Toast.makeText(this, "Пожалуйста, заполните все поля корректно", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val newItem = Item(
                id = 0, // ID будет автоматически сгенерирован базой
                image = "", // Можно добавить загрузку изображения
                brand = brand,
                desc = desc,
                text = desc,
                price = price,
                year = 2024, // Пример данных
                transmission = "Автомат", // Пример данных
                engineSize = 2.0,
                fuel = "Бензин",
                body = "Седан",
                mileage = 0
            )

            dbHelper.addItem(newItem)

            Toast.makeText(this, "Товар сохранён!", Toast.LENGTH_LONG).show()
            finish() // Закрываем SellActivity после сохранения
        }
    }
}
