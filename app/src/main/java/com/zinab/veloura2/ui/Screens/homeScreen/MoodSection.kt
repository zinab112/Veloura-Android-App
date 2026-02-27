package com.zinab.veloura2.ui.Screens.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zinab.veloura2.R

data class Mood(
    val name: String,
    val imageRes: Int
)

// قائمة moods
val moods = listOf(
    Mood("Effortlessly Chic", R.drawable.chic),
    Mood("Coastal Calm", R.drawable.calm),
    Mood("Night Luxe", R.drawable.luxe),
    Mood("Artsy Spirit", R.drawable.sprite),
    Mood("Earthy Elegant", R.drawable.date),
    Mood("Coffee Date", R.drawable.coffe)
)

@Composable
fun MoodItem(mood: Mood, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
    ) {
        // الصورة
        Image(
            painter = painterResource(id = mood.imageRes),
            contentDescription = mood.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // المستطيل للنص في الأسفل
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color(0x99000000),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = mood.name,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MoodSectionGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // 3 عناصر بالصف
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // ارتفاع الشبكة
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false
    ) {
        items(moods) { mood ->
            MoodItem(
                mood = mood,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // يخلي العنصر مربع ويكبر حسب الشبكة
            )
        }
    }
}
