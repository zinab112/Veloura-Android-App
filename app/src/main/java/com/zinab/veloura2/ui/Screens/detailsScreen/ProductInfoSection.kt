package com.zinab.veloura2.ui.Screens.detailsScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ProductInfoSection() {
    Column(modifier = Modifier.padding(16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$360",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$420",
                textDecoration = TextDecoration.LineThrough,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "⭐ 4.7 (189)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Brown Jacket",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "Fashion Brand",
            color = Color.Gray
        )
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
@Composable
fun ProductInfoSectionPreview() {
    ProductInfoSection()
}