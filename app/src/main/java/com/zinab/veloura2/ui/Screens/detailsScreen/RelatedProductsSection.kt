package com.zinab.veloura2.ui.Screens.detailsScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun RelatedProductsSection() {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "You may also like", fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(5) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .padding(end = 12.dp)
                        .background(Color.LightGray, RoundedCornerShape(12.dp))
                )
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
@Composable
fun RelatedProductsSectionPreview() {
    RelatedProductsSection()
}