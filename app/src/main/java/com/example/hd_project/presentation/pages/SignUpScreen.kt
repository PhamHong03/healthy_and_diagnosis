package com.example.hd_project.presentation.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hd_project.R
import com.example.hd_project.components.LoginTextField
import com.example.hd_project.ui.theme.Black
import com.example.hd_project.ui.theme.BlueGray
import com.example.hd_project.ui.theme.Roboto
import com.example.hd_project.viewModel.AuthViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopSection("ĐĂNG KÝ")
        Spacer(modifier = Modifier.height(20.dp))
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ){

            SignUpSection(navController,authViewModel)

            Spacer(modifier = Modifier.height(25.dp))
//            SocialMediaSection(authViewModel)
            val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
            Box(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.8f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ){
                LoginPrompt(uiColor, navController)
            }
        }
    }
}

@Composable
fun LoginPrompt(uiColor: Color, navController: NavController) {
    val signUpText = "Đăng nhập"
    val fullText = "Bạn đã có tài khoản? $signUpText"

    // xac dinh vi tri bat dau cua dang ky
    val signupStartIndex = fullText.indexOf((signUpText))

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color(0xFF94A3B8),
                fontSize = 14.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Bạn đã có tài khoản? ")
        }
        withStyle(
            style = SpanStyle(
                color = uiColor,
                fontSize = 14.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(signUpText)
        }
    }
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            if (offset in signupStartIndex until (signupStartIndex + signUpText.length)) {
                navController.navigate("login")
            }
        }
    )
}
@Composable
private fun SignUpSection(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember { mutableStateOf(false) }

//    val context = LocalContext.current
//    val authState = authViewModel.authState.observeAsState()
//
//    LaunchedEffect(authState.value) {
//        when(authState.value){
//            is AuthState.Authenticated -> navController.navigate("home")
//            is AuthState.Error -> Toast.makeText(context,
//                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
//            else -> Unit
//        }
//    }
    LoginTextField(
        label = "Tên của bạn",
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Toggle visibility"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textInput = username,
        onTextChanged = {username = it}
    )
    Spacer(modifier = Modifier.height(10.dp))
    LoginTextField(
        label = "Số điện thoại",
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = "Toggle visibility"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textInput = phone,
        onTextChanged = {phone = it}
    )
    Spacer(modifier = Modifier.height(10.dp))
    LoginTextField(
        label = "Email",
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Toggle visibility"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textInput = email,
        onTextChanged = {email = it}
    )
    Spacer(modifier = Modifier.height(10.dp))
    LoginTextField(
        label = "Mật khẩu",
        trailingIcon = {
            Icon(
                imageVector = if (isPasswordVisible) Icons.Filled.Done else Icons.Filled.Lock,
                contentDescription = if (isPasswordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu",
                modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textInput = password,
        onTextChanged = { password = it },
        isPasswordField = true,
        isPasswordVisible = isPasswordVisible
    )
    Spacer(modifier = Modifier.height(30.dp))
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            authViewModel.signup(email,password,username, phone)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) BlueGray else Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = "ĐĂNG KÝ",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Composable
fun TopSection(text: String) {
    val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.46f),
            painter = painterResource(id = R.drawable.shape),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier.padding(top = 80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(42.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(
                    id = R.string.app_logo
                ),
                tint = uiColor
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column() {
                Text(
                    text = stringResource(id = R.string.the_logo_text),
                    style = MaterialTheme.typography.headlineMedium,
                    color = uiColor
                )
                Text(
                    text = stringResource(id = R.string.find_house),
                    style = MaterialTheme.typography.titleMedium,
                    color = uiColor
                )
            }
        }
        Text(
            modifier = Modifier.run { padding(bottom = 10.dp).align(alignment = Alignment.BottomCenter) },
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            color = uiColor
        )

    }
}

