package ru.itis.summerpractice.tudasyudaproject.model

import kotlinx.serialization.Serializable

@Serializable
data class CityData (
    val name: String,
    val imageUrl: String,
    val population: Long,
    val area: Float,
    val description: String = ""
)