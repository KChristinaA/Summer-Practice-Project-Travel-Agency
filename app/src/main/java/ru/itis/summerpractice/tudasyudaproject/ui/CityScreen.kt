package ru.itis.summerpractice.tudasyudaproject.ui

import androidx.compose.runtime.Composable
import ru.itis.summerpractice.tudasyudaproject.CurrentData
import ru.itis.summerpractice.tudasyudaproject.utils.ConvertCityIndexToCity

@Composable
fun CityScreen(onBackClick: () -> Unit) {
    val city = ConvertCityIndexToCity(CurrentData.currentSelectedCity)

}