package com.zinab.veloura2.ui.Screens.homeScreen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeader(
    title: String,
    onViewAllClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // Section Title
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        // View All (optional)
        if (onViewAllClick != null) {
            Text(
                text = "View all",
                color = Color.LightGray,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onViewAllClick() }
            )
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
@Composable
fun SectionHeaderPreview() {
    SectionHeader(
        title = "New Arrivals",
        onViewAllClick = {}
    )
}
