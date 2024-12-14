package com.example.registrationpage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class SellActivity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        val dbHelper = DbHelper(this, null)

        // Получаем все EditText поля
        val brandInput: EditText = findViewById(R.id.editTextBrand)
        val descInput: EditText = findViewById(R.id.editTextDesc)
        val priceInput: EditText = findViewById(R.id.editTextPrice)
        val yearInput: EditText = findViewById(R.id.editTextYear)
        val transmissionInput: EditText = findViewById(R.id.editTextTransmission)
        val engineSizeInput: EditText = findViewById(R.id.editTextEngineSize)
        val fuelInput: EditText = findViewById(R.id.editTextFuel)
        val bodyInput: EditText = findViewById(R.id.editTextBody)
        val mileageInput: EditText = findViewById(R.id.editTextMileage)
        val imageView: ImageView = findViewById(R.id.imageViewSell)

        val chooseImageButton: Button = findViewById(R.id.btnChooseImage)
        val saveButton: Button = findViewById(R.id.btnAddItem)

        // Обработчик выбора изображения
        chooseImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_REQUEST)
        }

        // Обработчик сохранения товара
        saveButton.setOnClickListener {
            val brand = brandInput.text.toString()
            val desc = descInput.text.toString()
            val price = priceInput.text.toString().toDoubleOrNull()
            val year = yearInput.text.toString().toIntOrNull()
            val transmission = transmissionInput.text.toString()
            val engineSize = engineSizeInput.text.toString().toFloatOrNull()
            val fuel = fuelInput.text.toString()
            val body = bodyInput.text.toString()
            val mileage = mileageInput.text.toString().toIntOrNull()

            if (brand.isEmpty() || desc.isEmpty() || price == null || year == null ||
                transmission.isEmpty() || engineSize == null || fuel.isEmpty() ||
                body.isEmpty() || mileage == null || selectedImageUri == null) {
                Toast.makeText(this, "Пожалуйста, заполните все поля корректно", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val newItem = Item(
                id = 0,
                image = selectedImageUri.toString(),  // Сохраняем URI изображения
                brand = brand,
                desc = desc,
                text = desc,
                price = price.toInt(),
                year = year,
                transmission = transmission,
                engineSize = engineSize,
                fuel = fuel,
                body = body,
                mileage = mileage
            )

            dbHelper.addItem(newItem)

            Toast.makeText(this, "Товар сохранён!", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    // Обработка выбора изображения
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            findViewById<ImageView>(R.id.imageViewSell).setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val IMAGE_PICK_REQUEST = 1
    }
}
