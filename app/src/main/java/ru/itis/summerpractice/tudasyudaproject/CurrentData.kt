package ru.itis.summerpractice.tudasyudaproject

object CurrentData {
    var currentUser: Person? = null
    var currentSelectedCity: Int = -1

    fun exitFromProfile() {
        currentUser = null
    }
    fun selectCity(index: Int) {
        currentSelectedCity = index
    }

    fun clearSelectedCity() {
        currentSelectedCity = -1
    }
}