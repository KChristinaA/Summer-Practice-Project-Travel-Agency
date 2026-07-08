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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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

    Box(modifier = Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .size(350.dp)
                .clip(CircleShape)
                .background(Color(0xFF9370DB).copy(alpha = 0.5f))
                .align(Alignment.TopStart)
                .offset(x = (-60).dp, y = (-60).dp)
        )

        Spacer(
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape)
                .background(Color(0xFF9400D3).copy(alpha = 0.5f))
                .align(Alignment.BottomEnd)
                .offset(x = (-60).dp, y = (-60).dp)
        )

        Spacer(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(Color(0xFF6A5ACD).copy(alpha = 0.4f))
                .align(Alignment.TopStart)
                .offset(x = (500).dp, y = (-500).dp)
        )

        Spacer(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color(0xFF4B0082).copy(alpha = 0.4f))
                .align(Alignment.BottomEnd)
                .offset(x = (-50).dp, y = 50.dp)
        )

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

            if (isRegister) {
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = repeatPassword,
                    onValueChange = {
                        repeatPassword = it; errorMessage = null; successMessage = null
                    },
                    placeholder = { Text("Повторите пароль", fontSize = 24.sp) },
                    textStyle = TextStyle(fontSize = 24.sp, color = Color.Black),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isRegister) "Зарегистрироваться" else "Войти",
                    fontSize = 24.sp
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
                    color = Color(0xFF483D8B),
                    fontSize = 20.sp
                )
            }
        }
    }
}