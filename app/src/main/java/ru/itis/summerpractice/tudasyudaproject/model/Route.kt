package ru.itis.summerpractice.tudasyudaproject.model

data class Route (
    val name: String,
    val image: Int,
    val description: String,
    val time: Float, //время в часах
    val length: Float, //Протяженность в км
    val cityIndex: Int
)