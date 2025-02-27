package com.example.hd_project.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hd_project.presentation.pages.TopSection

@Composable
fun InputNoCertificate(
    onClick: () -> Unit
) {
    var noCertificate by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun onClickWithValidation() {
        val error = validateInput(noCertificate)
        errorMessage = error
        if (error == null) {
            onClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        TopSection(text = "ĐỊNH DANH")
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = noCertificate,
            onValueChange = {
                noCertificate = it
                errorMessage = validateInput(it)
            },
            label = {
                Text(
                    text = "Nhập định danh Bác sĩ!",
                )
            },
            isError = errorMessage != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(6.dp))
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                modifier = Modifier.padding(start = 20.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ButtonClick(onClick = ::onClickWithValidation, isEnable = errorMessage == null)
    }

}
fun validateInput(input: String): String? {
    return when {
        input.isBlank() -> "Vui lòng nhập định danh!"
        input.length != 6 -> "Vui lòng nhập đúng số định danh!"
        !input.all { it.isDigit() } -> "Định danh chỉ được chứa số!"
        else -> null
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonClick(
    onClick:()->Unit,
    isEnable: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            enabled = isEnable,
            modifier = Modifier.fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(Color(0xFF42A5F5))
        ) {
            Text(
                modifier = Modifier.padding(7.dp),
                text = "Tiếp tục",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewInput(){
    InputNoCertificate(onClick = {})
}