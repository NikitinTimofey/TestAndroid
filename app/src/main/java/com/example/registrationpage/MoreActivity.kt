package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrationpage.data.Item
import com.example.registrationpage.databinding.ActivityMoreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoreBinding
    private lateinit var adapter: UserItemsAdapter
    private val userItems = mutableListOf<Item>() // Объявления текущего пользователя
    private val firestore = FirebaseFirestore.getInstance()
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Установка email пользователя
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        binding.profileEmail.text = userEmail ?: "Email не найден"

        // Кнопка выхода
        binding.logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        // Настройка RecyclerView
        setupRecyclerView()

        // Загрузка объявлений
        loadUserItems()
    }

    private fun setupRecyclerView() {
        adapter = UserItemsAdapter(userItems, this) { item, position ->
            deleteItem(item, position)
        }
        binding.moreItemsList.layoutManager = LinearLayoutManager(this)
        binding.moreItemsList.adapter = adapter
    }

    private fun loadUserItems() {
        if (currentUserId == null) {
            Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
            return
        }

        firestore.collection("Items")
            .whereEqualTo("userId", currentUserId) // Фильтрация по текущему пользователю
            .get()
            .addOnSuccessListener { result ->
                userItems.clear()
                for (document in result) {
                    val item = Item(
                        id = document.id.hashCode(),
                        imageUrl = document.getString("imageBase64") ?: "",
                        brand = document.getString("brand") ?: "Unknown",
                        text = document.getString("description") ?: "No description",
                        price = document.getLong("price")?.toInt() ?: 0,
                        year = document.getLong("year")?.toInt() ?: 0,
                        transmission = document.getString("transmission") ?: "Unknown",
                        engineSize = document.getDouble("engineSize")?.toFloat() ?: 0f,
                        fuel = document.getString("fuel") ?: "Unknown",
                        body = document.getString("body") ?: "Unknown",
                        mileage = document.getLong("mileage")?.toInt() ?: 0,
                        userId = document.getString("userId") ?: "",
                        documentId = document.id
                    )
                    userItems.add(item)
                }
                adapter.updateList(userItems)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Ошибка загрузки: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteItem(item: Item, position: Int) {
        firestore.collection("Items").document(item.documentId)
            .delete()
            .addOnSuccessListener {
                userItems.removeAt(position) // Удаляем элемент из списка
                adapter.notifyItemRemoved(position)
                Toast.makeText(this, "Объявление удалено", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Ошибка при удалении: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
