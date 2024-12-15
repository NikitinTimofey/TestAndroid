package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationpage.data.Item
import com.example.registrationpage.databinding.ActivityItemsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val itemsList: RecyclerView = findViewById(R.id.ItemsList)
        val items = arrayListOf<Item>()

        // Создание бд
       /* val fs = Firebase.firestore
        fs.collection("Items")
            .document().set(mapOf("brand" to "Audi", "text" to "Описание Audi"))*/


        items.add(Item(1, "honda", "Honda", "Описание Honda", 500, 2000, "manual", 1.6f, "gas", "sedan", 10000))
        /*items.add(Item(3, "audi", "Audi", "Описание Audi", "Полный текст Audi", 100, 2021, "Робот", 1.8f, "HYBRID", "Универсал", 25000))*/

        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ItemsAdapter(items, this)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {}
                R.id.favourites -> {}
                R.id.sell -> {
                    val intent = Intent(this, SellActivity::class.java)
                    startActivity(intent)
                }
                R.id.more -> {}
            }
            true
        }
    }
}