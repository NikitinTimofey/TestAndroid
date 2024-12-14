package com.example.registrationpage

class Item(
    val id: Int,
    val image: String,
    val brand: String,
    val desc: String,
    val text:String,
    val price: Int,
    val year: Int,
    val transmission: String,
    val engineSize: Float, // объём двигателя
    val fuel: String,
    val body: String, // тип кузова
    val mileage: Int
) {
}