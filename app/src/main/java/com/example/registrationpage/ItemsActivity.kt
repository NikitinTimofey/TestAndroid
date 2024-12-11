package com.example.registrationpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationpage.databinding.ActivityItemsBinding

class ItemsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val itemsList: RecyclerView = findViewById(R.id.ItemsList)
        val items = arrayListOf<Item>()

        items.add(Item(1, "honda", "Honda", "Описание Honda", "Полный текст Honda", 500, 2023, "Автомат", 2.0f, "GASOLINE", "Седан", 10000))
        items.add(Item(2, "bmw", "BMW", "Описание BMW", "Полный текст BMW", 600, 2022, "Механика", 3.0f, "DIESEL", "Хэтчбек", 50000))
        items.add(Item(3, "audi", "Audi", "Описание Audi", "Полный текст Audi", 100, 2021, "Робот", 1.8f, "HYBRID", "Универсал", 25000))

        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ItemsAdapter(items, this)
    }
}