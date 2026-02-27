package com.zinab.veloura2.ui.Screens.homeScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zinab.veloura2.R


@Composable
fun PromoBannerRow() {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PromoBanner(imageRes = R.drawable.promo3, title = "50% Off!")
        PromoBanner(imageRes = R.drawable.promo1, title = "New Arrival")
        PromoBanner(imageRes =  R.drawable.promo1, title = "Limited Edition")
    }
}

@Composable
fun PromoBanner(imageRes: Int, title: String) {
    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 120.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = title,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PromoBannerRowPreview() {
    PromoBannerRow()
}
