package com.zinab.veloura2.ui.Screens.detailsScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun ReviewsSection() {
    Column(modifier = Modifier.padding(16.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Reviews", fontWeight = FontWeight.Medium)
            Text(text = "See All", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "⭐ 4.7 based on 189 reviews")
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
@Composable
fun ReviewsSectionPreview() {
    ReviewsSection()
}