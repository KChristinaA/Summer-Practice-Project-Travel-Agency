package ru.itis.summerpractice.tudasyudaproject.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.summerpractice.tudasyudaproject.Person
import ru.itis.summerpractice.tudasyudaproject.UserDatabase

@Composable
fun AuthScreen(onLoginSuccess: (Person) -> Unit) {
    val context = LocalContext.current

    var isRegister by remember { mutableStateOf(false) }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var passwordStrength by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xfff8f7ff)).clipToBounds()) {
        if (!isRegister) {
            Spacer(
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFd3bffd).copy(alpha = 0.7f))
                    .align(Alignment.TopEnd)
                    .offset(x = (-180).dp, y = (180).dp)
            )

            Spacer(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFc391de).copy(alpha = 0.5f))
                    .align(Alignment.TopStart)
                    .offset(x = (60).dp, y = (100).dp)
            )
        } else {
            Spacer(
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFd3bffd).copy(alpha = 0.6f))
                    .align(Alignment.TopEnd)
                    .offset(x = (-80).dp, y = (-100).dp)
            )

            Spacer(
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFc391de).copy(alpha = 0.5f))
                    .align(Alignment.TopEnd)
                    .offset(x = (-80).dp, y = (-80).dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = login,
                onValueChange = { login = it; errorMessage = null; successMessage = null },
                placeholder = { Text("Логин", fontSize = 24.sp) },
                textStyle = TextStyle(fontSize = 24.sp, color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                    if (isRegister) {
                        if (it.isEmpty()) {
                            passwordStrength = null
                        } else {
                            passwordStrength = UserDatabase.checkPassword(it)
                        }
                    }
                },
                placeholder = { Text("Пароль", fontSize = 24.sp) },
                textStyle = TextStyle(fontSize = 24.sp, color = Color.Black),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (isRegister && passwordStrength != null) {
                Spacer(modifier = Modifier.height(8.dp))
                val strength = when (passwordStrength) {
                    "Надежный" -> Color(0xFF228B22)
                    "Хороший" -> Color(0xFF0000CD)
                    "Средний" -> Color(0xFFFF8C00)
                    else -> Color(0xFF8B0000)
                }
                Text(
                    text = "Сложность: $passwordStrength",
                    color = strength,
                    fontSize = 20.sp
                )
            }

fun AuthScreen(navController: NavController) {

}