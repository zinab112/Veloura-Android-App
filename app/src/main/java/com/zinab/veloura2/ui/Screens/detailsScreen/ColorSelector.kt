package com.zinab.veloura2.ui.Screens.detailsScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColorSelectorSection(onColorSelected: (Color) -> Unit, selectedColor: Color) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Color")

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            ColorItem(Color(0xFF6B4F4F))
            ColorItem(Color(0xFF2E7D32))
            ColorItem(Color(0xFF5E35B1))
        }
    }
}

@Composable
fun ColorItem(color: Color) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .padding(end = 8.dp)
            .background(color, CircleShape)
            .border(1.dp, Color.Black, CircleShape)
    )
}
@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
@Composable
fun ColorSelectorSectionPreview() {
}