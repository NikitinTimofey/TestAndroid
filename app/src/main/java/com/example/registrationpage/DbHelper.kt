package com.example.registrationpage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Создаём таблицу пользователей
        val userTableQuery = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, email TEXT, pass TEXT)"
        db!!.execSQL(userTableQuery)

        // Создаём таблицу товаров
        val itemsTableQuery = """
        CREATE TABLE items (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            image TEXT,
            brand TEXT,
            desciption TEXT,
            text TEXT,
            price REAL,
            year INTEGER,
            transmission TEXT,
            engineSize REAL,
            fuel TEXT,
            body TEXT,
            mileage INTEGER
        )
    """
        db.execSQL(itemsTableQuery)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("pass", user.pass)

        val db = this.writableDatabase
        db.insert("users", null, values)

        db.close()
    }

    fun getUser(login: String, pass: String): Boolean {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null)
        return result.moveToFirst()
    }

    fun addItem(item: Item) {
        val values = ContentValues().apply {
            put("image", item.image)
            put("brand", item.brand)
            put("desc", item.desc)
            put("text", item.text)
            put("price", item.price)
            put("year", item.year)
            put("transmission", item.transmission)
            put("engineSize", item.engineSize)
            put("fuel", item.fuel)
            put("body", item.body)
            put("mileage", item.mileage)
        }

        val db = writableDatabase
        db.insert("items", null, values)
        db.close()
    }

    fun getAllItems(): List<Item> {
        val items = mutableListOf<Item>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM items", null)

        if (cursor.moveToFirst()) {
            do {
                val item = Item(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    image = cursor.getString(cursor.getColumnIndexOrThrow("image")),
                    brand = cursor.getString(cursor.getColumnIndexOrThrow("brand")),
                    desc = cursor.getString(cursor.getColumnIndexOrThrow("desciption")),
                    text = cursor.getString(cursor.getColumnIndexOrThrow("text")),
                    price = cursor.getInt(cursor.getColumnIndexOrThrow("price")),
                    year = cursor.getInt(cursor.getColumnIndexOrThrow("year")),
                    transmission = cursor.getString(cursor.getColumnIndexOrThrow("transmission")),
                    engineSize = cursor.getFloat(cursor.getColumnIndexOrThrow("engineSize")),
                    fuel = cursor.getString(cursor.getColumnIndexOrThrow("fuel")),
                    body = cursor.getString(cursor.getColumnIndexOrThrow("body")),
                    mileage = cursor.getInt(cursor.getColumnIndexOrThrow("mileage"))
                )
                items.add(item)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return items
    }





}