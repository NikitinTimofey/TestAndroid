package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationpage.databinding.ActivityItemsBinding

class ItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemsBinding
    private lateinit var itemsAdapter: ItemsAdapter // Добавлено поле для адаптера
    private lateinit var recyclerView: RecyclerView // Это поле для RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        enableEdgeToEdge() // Включение режима edge-to-edge
        setContentView(binding.root)

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.ItemsList) // Находим RecyclerView по id
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Загружаем данные из базы
        val dbHelper = DbHelper(this, null)
        val itemsList = dbHelper.getAllItems()

        // Инициализируем адаптер
        itemsAdapter = ItemsAdapter(itemsList, this)
        recyclerView.adapter = itemsAdapter

        // Получаем текущий логин из Intent
        val currentLogin = intent.getStringExtra("currentLogin")

        // Устанавливаем слушатель для BottomNavigationView
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {}
                R.id.favourites -> {
                    Toast.makeText(this, "fav", Toast.LENGTH_LONG).show()
                }
                R.id.sell -> {
                    val intent = Intent(this, SellActivity::class.java)
                    startActivity(intent)
                }
                R.id.more -> {
                    val intent = Intent(this, MoreActivity::class.java)
                    intent.putExtra("currentLogin", currentLogin)
                    startActivity(intent)
                }
            }
            true
        }

        // Настроим окно для отступов с учётом системных панелей
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        // Обновляем данные при возвращении на экран
        val dbHelper = DbHelper(this, null)
        val itemsList = dbHelper.getAllItems()
        itemsAdapter.updateData(itemsList) // Обновление данных адаптера
    }
}
