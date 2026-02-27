package com.zinab.veloura2.ui.Screens.detailsScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun SizeSelectorSection(onSizeSelected: (String) -> Unit, selectedSize: String) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Size")

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            listOf("M", "L", "XL", "XXL").forEach {
                SizeItem(it)
            }
        }
    }
}

@Composable
fun SizeItem(size: String) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = size)
    }
}
//@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
//@Composable
//fun SizeSelectorSectionPreview() {
//    SizeSelectorSection()
//}