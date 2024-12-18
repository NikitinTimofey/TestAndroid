package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrationpage.data.Item
import com.example.registrationpage.databinding.ActivityItemsBinding
import com.google.firebase.firestore.FirebaseFirestore

class ItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemsBinding
    private val items = arrayListOf<Item>()
    private lateinit var adapter: ItemsAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ItemsAdapter(items, this)
        binding.ItemsList.layoutManager = LinearLayoutManager(this)
        binding.ItemsList.adapter = adapter

        loadItemsFromFirestore()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.sell -> {
                    startActivity(Intent(this, SellActivity::class.java))
                }
                R.id.more -> {
                    startActivity(Intent(this, MoreActivity::class.java))
                }
                else -> {}
            }
            true
        }
    }

    private fun loadItemsFromFirestore() {
        firestore.collection("Items")
            .get()
            .addOnSuccessListener { result ->
                items.clear()
                for (document in result) {
                    val item = Item(
                        id = document.id.hashCode(),
                        documentId = document.id,
                        imageUrl = document.getString("imageBase64") ?: "",
                        brand = document.getString("brand") ?: "Unknown",
                        contactInfo = document.getString("description") ?: "No description",
                        price = document.getLong("price")?.toInt() ?: 0,
                        year = document.getLong("year")?.toInt() ?: 0,
                        transmission = document.getString("transmission") ?: "Unknown",
                        engineSize = document.getDouble("engineSize")?.toFloat() ?: 0f,
                        fuel = document.getString("fuel") ?: "Unknown",
                        body = document.getString("body") ?: "Unknown",
                        mileage = document.getLong("mileage")?.toInt() ?: 0,
                        userId = document.getString("userId") ?: ""
                    )
                    items.add(item)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                println("Ошибка при загрузке данных: ${e.message}")
            }
    }

    override fun onResume() {
        super.onResume()
        loadItemsFromFirestore()
    }
}
