package ru.itis.summerpractice.tudasyudaproject.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.itis.summerpractice.tudasyudaproject.model.AuthScreen
import ru.itis.summerpractice.tudasyudaproject.model.CityScreen
import ru.itis.summerpractice.tudasyudaproject.model.MainScreen
import ru.itis.summerpractice.tudasyudaproject.model.ProfileScreen

@Composable
fun AppRoot(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AuthScreen
    ) {

        composable<AuthScreen>{
        }

        composable<MainScreen>{
        }

        composable<CityScreen>{
        }

        composable<ProfileScreen>{
        }
    }
}