package ru.itis.summerpractice.tudasyudaproject.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ru.itis.summerpractice.tudasyuda.R
import ru.itis.summerpractice.tudasyudaproject.CurrentData
import ru.itis.summerpractice.tudasyudaproject.model.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onMainClick: () -> Unit,
    onRouteClick: (Int) -> Unit,
    onExitClick: () -> Unit) {
    val userName = CurrentData.currentUser?.login ?: "Неавторизованный пользователь"
    var selectedScreen by remember { mutableStateOf(1) }
    //TODO: вытаскивать список избранного у пользователя
    var favoriteRoutes by remember { mutableStateOf(emptyList<Route>())}
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.app_name_in_russian_uppercase), fontSize = TextUnit(24f, TextUnitType.Sp), fontWeight = FontWeight.Bold) },
                navigationIcon = {},
                actions = {},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFD6CAEA)
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFD6CAEA)
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.main_screen_title)) },
                    label = { Text(text = stringResource(R.string.main_screen_title)) },
                    selected = selectedScreen == 0,
                    onClick = { selectedScreen = 0
                    onMainClick()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = stringResource(R.string.profile_screen_title)) },
                    label = { Text(text = stringResource(R.string.profile_screen_title)) },
                    selected = selectedScreen == 1,
                    onClick = { selectedScreen = 1
                    }
                )
            }
        }) { it ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp),
                    tint = Color(0xFF483D8B)
                )

                Text(text = userName,
                    fontSize = TextUnit(22f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.padding(16.dp))

                Text(text = "Избранные маршруты:",
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    modifier = Modifier.align(Alignment.Start)
                        .padding(horizontal = 8.dp),
                    fontWeight = FontWeight.SemiBold)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 8.dp)
                        .border(
                            width = 2.dp,
                            color = Color(0xFF483D8B),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(4.dp)
                ) {
                    if (favoriteRoutes.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(176.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Чтобы добавить маршрут в избранное,\nнажмите на кнопку рядом с ним в меню города",
                                    textAlign = TextAlign.Center,
                                    fontSize = TextUnit(16f, TextUnitType.Sp),
                                    color = Color.Gray
                                )
                            }
                        }
                    } else {
                        items(count = favoriteRoutes.size) { index ->
                            Text(text = favoriteRoutes.get(index).name,
                                fontSize = TextUnit(18f, TextUnitType.Sp),
                                modifier = Modifier.clickable {
                                    onRouteClick(favoriteRoutes.get(index).indexOfCity)
                                })
                            }
                        }
                    }

                Spacer(modifier = Modifier.height(64.dp))

                Button(
                    onClick = {
                        onExitClick()
                    }
                ) {
                    Text(text = "Выйти из профиля")
                }
            }
        }
    }
}