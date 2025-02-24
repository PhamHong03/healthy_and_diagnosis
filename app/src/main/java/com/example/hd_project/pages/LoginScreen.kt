package com.example.hd_project.pages

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import com.example.hd_project.R
import com.example.hd_project.components.LoginTextField
import com.example.hd_project.components.SocialMediaLogin
import com.example.hd_project.ui.theme.Black
import com.example.hd_project.ui.theme.BlueGray
import com.example.hd_project.ui.theme.Roboto
import com.example.hd_project.viewModel.AuthState
import com.example.hd_project.viewModel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun LoginScreen(
    modifier: Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopSection("ĐĂNG NHẬP")
        Spacer(modifier = Modifier.height(30.dp))
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ){

            LoginSection(navController, authViewModel)
            Spacer(modifier = Modifier.height(25.dp))
            SocialMediaSection(authViewModel)
            val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
            Box(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.8f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ){
                SignUpPromt(uiColor, navController)
            }
        }
    }
}

@Composable
fun SignUpPromt(uiColor: Color, navController: NavController) {
    val signUpText = "Đăng ký"
    val fullText = "Bạn chưa có tài khoản? $signUpText"


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
            append("Bạn chưa có tài khoản? ")
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
                navController.navigate("signup")
            }
        }
    )
}

@Composable
fun SocialMediaSection(
    viewModel: AuthViewModel
) {
    val auth = Firebase.auth
    val signInState by viewModel.signInState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account.idToken?.let { viewModel.signInWithGoogle(it) }
        } catch (e: ApiException) {
            // Xử lý lỗi
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.continue_with),
            style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF647488))
        )

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialMediaLogin(
                icon = R.drawable.google,
                text = "Google",
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.launchGoogleSignIn(launcher)
                }
            )
            Spacer(modifier = Modifier.width(20.dp))
            SocialMediaLogin(
                icon = R.drawable.facebook,
                text = "Facebook",
                modifier = Modifier.weight(1f),
                onClick = {
//                    viewModel.signInWithGoogle()
                }
            )

        }

    }
}

@Composable
fun LoginSection(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoginTriggered by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()

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
    Spacer(modifier = Modifier.height(15.dp))
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
    Spacer(modifier = Modifier.height(7.dp))
    Box(modifier = Modifier){
        TextButton(onClick = {}) {
            Text(
                text = "Quên mật khẩu?",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
    Spacer(modifier = Modifier.height(7.dp))

    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Authenticated &&
            navController.currentDestination?.route != "home"
        ) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
//            isLoginTriggered = true
            authViewModel.login(email, password)
        },
        enabled = authState.value != AuthState.Loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) BlueGray else Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = "ĐĂNG NHẬP",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    val navController = TestNavHostController(LocalContext.current)
    val authViewModel: AuthViewModel = viewModel()

    LoginScreen(
        modifier = Modifier,
        navController = navController,
        authViewModel = authViewModel
    )
}
