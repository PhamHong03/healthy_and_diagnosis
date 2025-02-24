package com.example.hd_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.hd_project.ui.theme.HD_projectTheme
import com.example.hd_project.viewModel.AuthViewModel
import com.example.hd_project.viewModel.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("your-web-client-id")
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val authRepository = AuthRepository()

        val authViewModelFactory = AuthViewModelFactory(googleSignInClient, authRepository)
        val authViewModel: AuthViewModel by viewModels { authViewModelFactory }
        setContent {
            val navController = rememberNavController()
            HD_projectTheme {
                Scaffold (modifier = Modifier.fillMaxSize()){innerPadding->
                    MyAppNavigation(modifier = Modifier.padding(innerPadding),authViewModel = authViewModel)                }
            }
        }
    }
}



