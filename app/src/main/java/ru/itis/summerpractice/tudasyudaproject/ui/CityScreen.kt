package ru.itis.summerpractice.tudasyudaproject.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.summerpractice.tudasyudaproject.CurrentData
import ru.itis.summerpractice.tudasyudaproject.model.Route
import ru.itis.summerpractice.tudasyudaproject.repository.Cities
import ru.itis.summerpractice.tudasyudaproject.repository.CurrentData
import ru.itis.summerpractice.tudasyudaproject.repository.Favorites
import ru.itis.summerpractice.tudasyudaproject.repository.Routes
import ru.itis.summerpractice.tudasyudaproject.utils.ConvertCityIndexToCity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityScreen(onBackClick: () -> Unit) {
    val cityIndex = CurrentData.currentSelectedCity
    val city = ConvertCityIndexToCity(CurrentData.currentSelectedCity)
    val cities = Cities.getCities()
    val allRoutes = Routes.getRoutesForCity(cityIndex)
    var sort by remember { mutableStateOf("none") }
    var sortMenu by remember { mutableStateOf(false) }
    if (city == null) {
        onBackClick()
    }
    val sortedRoutes = remember(sort,allRoutes) {
        when(sort) {
            "none" -> allRoutes
            "time_up" -> allRoutes.sortedBy { it.time }
            "time_down" -> allRoutes.sortedByDescending { it.time }
            "length_up" -> allRoutes.sortedBy { it.length }
            "length_down" -> allRoutes.sortedByDescending { it.length }
            else -> allRoutes
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (city != null) {
                        Text(
                            text = city.name.uppercase(),
                            fontSize = 24.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {onBackClick()}) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color(0xFFDCDCDC))
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Информация о городе",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (city != null) {
                    Text(
                        text = city.description,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (city != null) {
                    Text(
                        text = "Площадь: ${city.area} км²",
                        fontSize = 15.sp
                    )
                }

                if (city != null) {
                    Text(
                        text = "Население: ${city.population} чел.",
                        fontSize = 15.sp
                    )
                }
            }

            item {
                Box {
                    OutlinedButton(
                        onClick = {sortMenu = true},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Сортировать: ${getSort(sort)}", fontSize = 18.sp)
                    }

                    DropdownMenu(
                        expanded = sortMenu,
                        onDismissRequest = {sortMenu = false}
                    ) {
                        DropdownMenuItem(
                            text = { Text("По времени (короткие сначала)", fontSize = 18.sp) },
                            onClick = {
                                sort = "time_up"
                                sortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("По времени (длинные сначала)", fontSize = 18.sp) },
                            onClick = {
                                sort = "time_down"
                                sortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("По протяженности (короткие сначала)", fontSize = 18.sp) },
                            onClick = {
                                sort = "length_up"
                                sortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("По протяженности (длинные сначала)", fontSize = 18.sp) },
                            onClick = {
                                sort = "length_down"
                                sortMenu = false
                            }
                        )
                    }
                }
            }
            items(sortedRoutes) { route ->
                RouteItem(route = route)
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

fun getSort(sort: String): String {
    return when(sort) {
        "none" -> "без сортировки"
        "time_up" -> "по времени ↑"
        "time_down" -> "по времени ↓"
        "length_up" -> "по протяженности ↑"
        "length_down" -> "по протяженности ↓"
        else -> "без сортировки"
    }
}

@Composable
fun RouteItem(route: Route) {
    var expanded by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(Favorites.isFavorite(route.name)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = route.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = {
                Favorites.switch(route.name)
                isFavorite = !isFavorite}) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Удалить из избранного" else "Добавить в избранное",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = {expanded = !expanded}) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Свернуть" else "Развернуть",
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = route.description,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(route.image),
                contentDescription = route.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Время: ${route.time} ч",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Протяженность: ${route.length} км",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}