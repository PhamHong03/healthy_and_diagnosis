package com.example.hd_project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hd_project.components.SelectRoleScreen
import com.example.hd_project.presentation.pages.DiagnosiScreen
import com.example.hd_project.presentation.pages.HomeScreen
import com.example.hd_project.presentation.pages.LoginScreen
import com.example.hd_project.presentation.pages.SignUpScreen
import com.example.hd_project.viewModel.AuthViewModel

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable(route = "login"){
            LoginScreen(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(route = "signup"){
            SignUpScreen(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(route = "select_role"){
            SelectRoleScreen(
                clickRoleDoctor = {
                    navController.navigate("home")
                },
                clickRoleCustomer = {
                    navController.navigate("customer_home")
                })
        }

        composable(route = "home"){
            HomeScreen(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(route = "patients" ){

        }
        composable(route = "diagnosis" ){
            DiagnosiScreen(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(route = "patient_info" ){

        }
        composable(route = "healthcare" ){

        }
        composable(route = "results" ){

        }
        composable(route = "notes" ){

        }
        composable(route = "meetings" ){

        }
        composable(route = "profile" ){

        }
        composable(route = "settings" ){

        }

    }
}