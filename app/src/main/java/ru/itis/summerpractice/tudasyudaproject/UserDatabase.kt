package ru.itis.summerpractice.tudasyudaproject

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Person(
    val login: String,
    val password: String,
    val favoriteRoutes: List<String> = emptyList()
)

object UserDatabase {
    private const val PREFS_NAME = "my_app_prefs"
    private const val KEY_USERS = "users_list"
    fun getUsers(context: Context): MutableList<Person> {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_USERS, null)

        return if (json != null) {
            val type = object : TypeToken<MutableList<Person>>() {}.type
            Gson().fromJson(json, type) ?: mutableListOf()
        } else {
            mutableListOf()
        }
    }

    fun saveUsers(context: Context, users: List<Person>) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(users)
        prefs.edit().putString(KEY_USERS, json).apply()
    }

    fun register(context: Context, login: String, pass1: String, pass2: String): String {
        if (login.isBlank()) return "Введите логин"
        if (pass1 != pass2) return "Пароли не совпадают"

        val check = checkPassword(pass1)
        if (check == "Ненадежный") return "Пароль слишком простой"

        val users = getUsers(context)
        if (users.any { it.login == login }) {
            return "Такой пользователь уже существует"
        }

        users.add(Person(login, pass1))
        saveUsers(context, users)

        return "success"
    }

    fun login(context: Context, login: String, password: String): String {
        val users = getUsers(context)
        val user = users.find { it.login == login }

        return when {
            user == null -> "Пользователь не существует, пройдите регистрацию"
            user.password != password -> "Пароль неверный"
            else -> "success"
        }
    }

    fun checkPassword(password: String): String {
        var check = 0
        if (password.length >= 8) check++
        if (password.any { it.isDigit() }) check++
        if (password.any { it.isUpperCase() }) check++
        if (password.any { it.isLowerCase() }) check++
        if (password.any { !it.isLetterOrDigit() }) check++

        return when (check) {
            5 -> "Надежный"
            4 -> "Хороший"
            3 -> "Средний"
            else -> "Ненадежный"
        }
    }

    private fun updateUser(context: Context, updatedUser: Person) {
        val users = getUsers(context)
        val index = users.indexOfFirst { it.login == updatedUser.login }
        users[index] = updatedUser
        saveUsers(context, users)
        CurrentData.currentUser = updatedUser
    }

    fun addFavoriteRoute(context: Context, routeName: String) {
        val user = CurrentData.currentUser ?: return
        val currentFavorites = user.favoriteRoutes
        if (currentFavorites.contains(routeName)) return
        val updatedUser = user.copy(favoriteRoutes = currentFavorites + routeName)
        updateUser(context, updatedUser)
    }

    fun removeFavoriteRoute(context: Context, routeName: String) {
        val user = CurrentData.currentUser ?: return
        val currentFavorites = user.favoriteRoutes
        if (!currentFavorites.contains(routeName)) return
        val updatedUser = user.copy(favoriteRoutes = currentFavorites.filter { it != routeName })
        updateUser(context, updatedUser)
    }
}
