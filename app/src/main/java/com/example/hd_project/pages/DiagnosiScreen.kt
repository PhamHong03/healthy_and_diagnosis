package com.example.hd_project.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hd_project.R
import com.example.hd_project.components.HeaderSection
import com.example.hd_project.viewModel.AuthViewModel

@Composable
fun DiagnosiScreen(
    modifier: Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
            HeaderSection(authViewModel)
            Diagnosis({ navController.navigate("camera") })
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.usecamera),
                fontSize = 14.sp, color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(2.dp)
//                    .background(MaterialTheme.colorScheme.primary)
                    .background(Color.Black)
            )


        }
    }
}

@Composable
fun Diagnosis(
    onClick: () -> Unit
) {
    Row {
        Image(
            modifier = Modifier
                .padding(top = 30.dp)
                .clickable { onClick() }
                .clip(RoundedCornerShape(40.dp)),
            painter = painterResource(id =  R.drawable.camera),
            contentDescription = ""
        )
    }
}


@Composable
fun Symptom(modifier: Modifier = Modifier) {

}
