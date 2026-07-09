package ru.itis.summerpractice.tudasyudaproject.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFf9f5fd))) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            if (!isRegister) {
                drawCircle(
                    color = Color(0xFF9370DB).copy(alpha = 0.4f),
                    radius = 250.dp.toPx(),
                    center = Offset(
                        x = 0.dp.toPx(),
                        y = 100.dp.toPx()
                    )
                )

                drawCircle(
                    color = Color(0xFFbb78f2).copy(alpha = 0.4f),
                    radius = 125.dp.toPx(),
                    center = Offset(
                        x = 150.dp.toPx(),
                        y = 250.dp.toPx()
                    )
                )
            } else {
                drawCircle(
                    color = Color(0xFF9370DB).copy(alpha = 0.5f),
                    radius = 250.dp.toPx(),
                    center = Offset(
                        x = 450.dp.toPx(),
                        y = 30.dp.toPx()
                    )
                )

                drawCircle(
                    color = Color(0xFFbb78f2).copy(alpha = 0.4f),
                    radius = 125.dp.toPx(),
                    center = Offset(
                        x = 300.dp.toPx(),
                        y = 165.dp.toPx()
                    )
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(250.dp))

            Text(
                text = if (isRegister) "Регистрация" else "Вход",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF390a62)
            )
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = if (isRegister) "Создайте новый аккаунт" else "Добро пожаловать!",
                fontSize = 20.sp,
                color = Color(0xFF6e6289)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = login,
                onValueChange = { login = it; errorMessage = null; successMessage = null },
                placeholder = { Text("Логин", fontSize = 18.sp) },
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffebd9f6),
                    unfocusedContainerColor = Color(0xfff3e8f9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Логин"
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
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
                placeholder = { Text("Пароль", fontSize = 18.sp) },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffebd9f6),
                    unfocusedContainerColor = Color(0xfff3e8f9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Пароль"
                    )
                }
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
            if (isRegister) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = repeatPassword,
                    onValueChange = {
                        repeatPassword = it; errorMessage = null; successMessage = null
                    },
                    placeholder = { Text("Повторите пароль", fontSize = 18.sp) },
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xffebd9f6),
                        unfocusedContainerColor = Color(0xfff3e8f9),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Повторите пароль"
                        )
                    }
                )
            }

            if (successMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = successMessage!!, color = Color(0xFF228B22), fontSize = 20.sp)
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = errorMessage!!, color = Color(0xFF8B0000), fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (isRegister) {
                        val res = UserDatabase.register(context, login, password, repeatPassword)
                        if (res == "success") {
                            successMessage = "Регистрация прошла успешно"
                            errorMessage = null
                            isRegister = false
                            login = ""
                            password = ""
                            repeatPassword = ""
                            passwordStrength = null
                        } else {
                            errorMessage = res
                            successMessage = null
                        }
                    } else {
                        val res = UserDatabase.login(context, login, password)
                        if (res == "success") {
                            val users = UserDatabase.getUsers(context)
                            val user = users.find { it.login == login }
                            if (user != null) {
                                onLoginSuccess(user)
                            }
                        } else {
                            errorMessage = res
                            successMessage = null
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff903dd2),
                    Color.White
                )
            ) {
                Text(
                    text = if (isRegister) "Зарегистрироваться" else "Войти",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    isRegister = !isRegister
                    errorMessage = null
                    successMessage = null
                    passwordStrength = null
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isRegister) "Уже есть аккаунт? Войти" else "Нет аккаунта? Зарегистрироваться",
                    color = Color(0xffc4abd4),
                    fontSize = 20.sp
                )
            }
        }
    }
}