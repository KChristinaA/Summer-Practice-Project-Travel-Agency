package ru.itis.summerpractice.tudasyudaproject.ui

import AuthScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.itis.summerpractice.tudasyudaproject.CurrentData
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
            AuthScreen(onLoginSuccess = { person ->
                navHostController.navigate(MainScreen)
                CurrentData.currentUser = person
            })
        }

        composable<MainScreen>{
            MainScreen(
                onCityClick = { index ->
                    CurrentData.selectCity(index)
                    navHostController.navigate(CityScreen)
                },
                onProfileClick = {
                    navHostController.navigate(ProfileScreen)
                })
        }

        composable<CityScreen>{
            CityScreen(onBackClick = {
                CurrentData.clearSelectedCity()
                navHostController.navigate(MainScreen)
            })
        }

        composable<ProfileScreen>{
            ProfileScreen(
                onMainClick = {
                    navHostController.navigate(MainScreen)
                },
                onRouteClick = { cityIndexOfRoute ->
                    CurrentData.selectCity(cityIndexOfRoute)
                    navHostController.navigate(CityScreen)
                },
                onExitClick = {
                    CurrentData.exitFromProfile()
                    navHostController.navigate(AuthScreen)
                }
            )
        }
    }
}