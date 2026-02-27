package com.zinab.veloura2.ui.Screens.homeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.zinab.veloura2.ui.Screens.homeScreen.viewmodel.ProductUiModel

// تعديل التوقيع: إضافة onProductClick
@Composable
fun ProductGridHorizontal(
    products: List<ProductUiModel>,
    onProductClick: (Int) -> Unit  // إضافة parameter جديد
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(products) { product ->
            Log.d("VELORA_UI", "Rendering: ${product.title} - ${product.price}")
            ProductImageCard(
                product = product,
                onProductClick = { onProductClick(product.id) },  // تمرير الـ id عند الضغط
                modifier = Modifier
                    .width(180.dp)
                    .height(220.dp)
            )
        }
    }
}

// تعديل ProductImageCard: إضافة onProductClick
@Composable
fun ProductImageCard(
    product: ProductUiModel,
    onProductClick: () -> Unit,  // إضافة parameter
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray)
            .clickable { onProductClick() },  // إضافة clickable
        contentAlignment = Alignment.BottomStart
    ) {
        androidx.compose.foundation.Image(
            painter = rememberAsyncImagePainter(product.imageUrl),
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(product.title, color = Color.White, fontSize = 16.sp)
            Text("$${product.price}", color = Color.White, fontSize = 14.sp)
        }
    }
}