package ru.itis.summerpractice.tudasyudaproject.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.itis.summerpractice.tudasyuda.R
import ru.itis.summerpractice.tudasyudaproject.repository.Cities

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onCityClick: (Int) -> Unit,
    onProfileClick: () -> Unit
) {
    val cities = Cities.getCities()
    var index = 0
    var selectedScreen by remember { mutableStateOf(0) }
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
                    onClick = { selectedScreen = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = stringResource(R.string.profile_screen_title)) },
                    label = { Text(text = stringResource(R.string.profile_screen_title)) },
                    selected = selectedScreen == 1,
                    onClick = { selectedScreen = 1
                        onProfileClick()
                    }
                )
            }
        }) { it ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(2) { x ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(2) { column ->
                            var index = row * 2 + column
                            if (index < cities.size) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(vertical = 16.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xFF483D8B),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(4.dp)
                                    .clickable {
                                        onCityClick(index)
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = cities[index].name.uppercase(),
                                    fontSize = TextUnit(21f, TextUnitType.Sp),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                AsyncImage(
                                    model = cities[index].imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(150.dp),
                                    contentScale = ContentScale.Crop
                                )
                                index++
                            }
                        }
                    }
                }
            }
        }
    }
}