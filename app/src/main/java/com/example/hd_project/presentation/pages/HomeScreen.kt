package com.example.hd_project.presentation.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import com.example.hd_project.R
import com.example.hd_project.components.HeaderSection
import com.example.hd_project.components.SearchBar
import com.example.hd_project.domain.model.MenuItemData
import com.example.hd_project.viewModel.AuthState
import com.example.hd_project.viewModel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        try {
            if (authState.value is AuthState.Unauthenticated &&
                navController.currentDestination?.route != "login"
            ) {
                Log.d("HomeScreen", "Navigating to login")
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }
        } catch (e: Exception) {
            Log.e("HomeScreen", "Navigation error", e)
        }
    }

    val items = listOf(
        MenuItemData("Danh sách", R.drawable.ic_patient, "patients", "5 Thông tin"),
        MenuItemData("Chuẩn đoán", R.drawable.ic_diagnose, "diagnosis", "3 Thông tin"),
//        MenuItemData("Thông Tin", R.drawable.ic_info, "patient_info", "5 Tin"),
        MenuItemData("CSSK", R.drawable.ic_healthcare, "healthcare", "7 Thông tin"),
        MenuItemData("Kết quả", R.drawable.ic_results, "results","5 Thông tin"),
        MenuItemData("Ghi chú", R.drawable.ic_notes, "notes", "9 Thông tin"),
        MenuItemData("Hội chuẩn", R.drawable.ic_meeting, "meetings", "5 Thông tin"),
        MenuItemData("Cá nhân", R.drawable.ic_personal, "profile", "2 Thông tin"),
        MenuItemData("Cài đặt", R.drawable.ic_settings, "settings", "5 Thông tin")
    )

//    val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
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

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSection(authViewModel)
            SearchBar()
            MenuGrid(items, navController)
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItemData, navController: NavController) {
    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFFFFF  ))
            .padding(16.dp)
            .clickable {
                navController.navigate(item.destination)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.title,
                tint = Color(0xFF008ECC),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            item.badgeCount?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = it, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}



@Composable
fun MenuGrid(menuItems: List<MenuItemData>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),  
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(menuItems) { item ->
            MenuItemCard(item, navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = TestNavHostController(LocalContext.current)
    val authViewModel: AuthViewModel = viewModel()

    HomeScreen(
        modifier = Modifier,
        navController = navController,
        authViewModel = authViewModel
    )
}
