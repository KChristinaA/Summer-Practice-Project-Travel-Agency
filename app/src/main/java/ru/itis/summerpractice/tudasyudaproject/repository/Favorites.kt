package ru.itis.summerpractice.tudasyudaproject.repository
import android.content.Context
import ru.itis.summerpractice.tudasyudaproject.CurrentData
import ru.itis.summerpractice.tudasyudaproject.UserDatabase
import ru.itis.summerpractice.tudasyudaproject.model.Route

object Favorites {
    fun switch(context: Context, routeName: String) {
        if (isFavorite(routeName)) {
            UserDatabase.removeFavoriteRoute(context, routeName)
        } else {
            UserDatabase.addFavoriteRoute(context, routeName)
        }
    }

    fun isFavorite(routeName: String): Boolean {
        val user = CurrentData.currentUser ?: return false
        return user.favoriteRoutes.contains(routeName)
    }

    fun getFavoriteRoutes(): List<Route> {
        val user = CurrentData.currentUser ?: return emptyList()
        val favoriteNames = user.favoriteRoutes
        return Routes.getAllRoutes().filter { favoriteNames.contains(it.name) }
    }

}