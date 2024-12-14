package com.example.registrationpage

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        // Получаем переданные данные

        /*val db = DbHelper(this, null)
        val userInfo = db.getUserData()*/




        val currentLogin = intent.getStringExtra("currentLogin")

        if (currentLogin != null) {
            val dbHelper = DbHelper(this, null)
            val db = dbHelper.readableDatabase

            // Запрос для получения данных о пользователе
            val cursor = db.rawQuery("SELECT login, email FROM users WHERE login = ?", arrayOf(currentLogin))
            if (cursor.moveToFirst()) {
                val login = cursor.getString(cursor.getColumnIndexOrThrow("login"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))

                // Найдите TextView из макета
                val loginTextView: TextView = findViewById(R.id.profile_login)
                val emailTextView: TextView = findViewById(R.id.profile_email)

                // Установите значения в TextView
                loginTextView.text = "Логин: $login"
                emailTextView.text = "Почта: $email"
            } else {
                Toast.makeText(this, "Данные пользователя не найдены", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
            db.close()
        } else {
            Toast.makeText(this, "Не удалось получить текущий логин", Toast.LENGTH_SHORT).show()
        }





        // Находим TextView для отображения логина и почты
        /*val loginTextView: TextView = findViewById(R.id.profile_login)*/

        // Устанавливаем полученные данные в TextView
        /*loginTextView.text = "Логин: ${userData.first}"*/



        // Устанавливаем полученные данные в TextView
        /*loginTextView.text = userLogin ?: "Логин не найден"
        emailTextView.text = userEmail ?: "Почта не найдена"*/
    }
}
