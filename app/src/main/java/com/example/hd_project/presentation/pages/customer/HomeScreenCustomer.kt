package com.example.hd_project.presentation.pages.customer

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hd_project.R
import com.example.hd_project.components.HeaderSection
import com.example.hd_project.components.SearchBar
import com.example.hd_project.viewModel.AuthViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreenCustomer(
    modifier: Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val notificationCount by remember {
        mutableStateOf(999)
    }
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
            HeaderSection(authViewModel = authViewModel, notificationCount, "Chào bạn: ")
            SearchBar()
            val section1 = listOf(
                Icons.Default.Person to "Hồ sơ",
                Icons.Default.Favorite to "Sức khỏe",
                Icons.Default.Edit to "Ghi chú",
                Icons.Default.Star to "Ưu đãi"
            )

            val section2 = listOf(
                Icons.Default.Notifications to "Nhắc nhở",
                Icons.Default.ShoppingCart to "Mua hàng",
//        Icons.Default.Message to "Hỗ trợ",
//        Icons.Default.Event to "Lịch sử"
            )

            val section3 = listOf(
//        Icons.Default.Analytics to "Báo cáo",
                Icons.Default.Settings to "Cài đặt",
//        Icons.Default.Help to "Trợ giúp",
                Icons.Default.Info to "Thông tin"
            )

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                GridSection("Dịch vụ", section1)
                GridSection("Tiện ích nhanh", section2)
                GridSection("Phân tích dữ liệu", section3)
            }
        }
    }
}
@Composable
fun GridItem(icon: ImageVector, title: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GridSection(title: String, items: List<Pair<ImageVector, String>>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { (icon, title) ->
                GridItem(
                    icon = icon,
                    title = title,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun HomeScreena() {
    val section1 = listOf(
        Icons.Default.Person to "Hồ sơ",
        Icons.Default.Favorite to "Sức khỏe",
        Icons.Default.Edit to "Ghi chú",
        Icons.Default.Star to "Ưu đãi"
    )

    val section2 = listOf(
        Icons.Default.Notifications to "Nhắc nhở",
        Icons.Default.ShoppingCart to "Mua hàng",
//        Icons.Default.Message to "Hỗ trợ",
//        Icons.Default.Event to "Lịch sử"
    )

    val section3 = listOf(
//        Icons.Default.Analytics to "Báo cáo",
        Icons.Default.Settings to "Cài đặt",
//        Icons.Default.Help to "Trợ giúp",
        Icons.Default.Info to "Thông tin"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        GridSection("Dịch vụ", section1)
        GridSection("Tiện ích nhanh", section2)
        GridSection("Phân tích dữ liệu", section3)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreena()
}