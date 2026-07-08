package ru.itis.summerpractice.tudasyudaproject.utils

import ru.itis.summerpractice.tudasyudaproject.model.CityData
import ru.itis.summerpractice.tudasyudaproject.repository.Cities

fun ConvertCityIndexToCity(index: Int): CityData? {
    val city: CityData?
    if (index != -1) {
        city = Cities.getCities().getOrNull(index)
    }
    else city = null
    return city
}