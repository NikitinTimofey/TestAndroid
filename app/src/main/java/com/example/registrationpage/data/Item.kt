package com.example.registrationpage.data

data class Item(
    val id: Int,
    val imageUrl: String,
    val brand: String,
    val text:String,
    val price: Int,
    val year: Int,
    val transmission: String,
    val engineSize: Float, // объём двигателя
    val fuel: String,
    val body: String, // тип кузова
    val mileage: Int
){}
