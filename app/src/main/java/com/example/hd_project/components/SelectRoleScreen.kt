package com.example.hd_project.components

import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hd_project.R

@Composable
fun SelectRoleScreen(
    clickRoleDoctor: () -> Unit,
    clickRoleCustomer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextChoseRole("Bạn là ai?")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42A5F5)),
            onClick = clickRoleDoctor
        ) {
            Text(text = stringResource(id = R.string.roleDoctor))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42A5F5)),
            onClick = clickRoleCustomer
        ) {
            Text(text = stringResource(id = R.string.roleCustomer))
        }
    }
}

@Composable
fun TextChoseRole(text: String) {
    Canvas(modifier = Modifier.size(250.dp, 120.dp)) {
        val path = Path().apply {
            val rect = RectF(0f, 0f, size.width, size.height * 3)
            this.addArc(rect, 180f, 180f)
        }

        val paint = Paint().apply {
            textSize = 120f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true

            shader = android.graphics.LinearGradient(
                0f, 0f, size.width, 0f,
                intArrayOf(
                    android.graphics.Color.parseColor("#42A5F5"),
                    android.graphics.Color.parseColor("#F48FB1")
                ),
                null,
                android.graphics.Shader.TileMode.CLAMP
            )

            setShadowLayer(10f, 8f, 8f, android.graphics.Color.GRAY)
        }

        drawContext.canvas.nativeCanvas.drawTextOnPath(
            text,
            path,
            0f,
            30f,
            paint
        )
    }
}
