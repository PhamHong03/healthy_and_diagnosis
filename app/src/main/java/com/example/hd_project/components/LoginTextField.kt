package com.example.hd_project.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.example.hd_project.ui.theme.focusedTextFieldText
import com.example.hd_project.ui.theme.textFieldContainer
import com.example.hd_project.ui.theme.unfocusedTextFieldText

class PasswordVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformed = "*".repeat(text.length)  // Thay thế ký tự bằng dấu sao
        return TransformedText(AnnotatedString(transformed), OffsetMapping.Identity)
    }
}

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    textInput: String,
    onTextChanged: (String) -> Unit,
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false
) {
    val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
    val visualTransformation = if (isPasswordField && !isPasswordVisible) {
        PasswordVisualTransformation()  // Sử dụng visual transformation tùy chỉnh
    } else {
        VisualTransformation.None  // Không áp dụng transformation nếu mật khẩu hiển thị
    }
    TextField(
        modifier = modifier,
        value = textInput,
        onValueChange = {
            onTextChanged(it)
        },
        label = {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = uiColor)
        },
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
            focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
            unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
            focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
        ),
        trailingIcon = trailingIcon
    )
}
