package ru.itis.summerpractice.tudasyudaproject.repository
import ru.itis.summerpractice.tudasyudaproject.model.Route

object Favorites {
    private val favoriteRoutes = mutableSetOf<String>()

    fun switch(routeName: String) {
        if (favoriteRoutes.contains(routeName)) {
            favoriteRoutes.remove(routeName)
        } else {
            favoriteRoutes.add(routeName)
        }
    }

    fun isFavorite(routeName: String): Boolean {
        return favoriteRoutes.contains(routeName)
    }

    fun getFavoriteRoutes(): List<Route> {
        return Routes.getAllRoutes().filter { favoriteRoutes.contains(it.name) }
    }

    fun getAll(): Set<String> {
        return favoriteRoutes.toSet()
    }
}