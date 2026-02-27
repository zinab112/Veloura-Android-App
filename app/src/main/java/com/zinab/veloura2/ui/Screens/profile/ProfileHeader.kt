package com.zinab.veloura2.ui.Screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.zinab.veloura2.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ProfileHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Profile",
            color = Color.White,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.person),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape) // الشكل دائري
                .border(
                    width = 2.dp, // سمك الإطار
                    color = Color.White.copy(alpha = 0.5f), // أبيض خفيف
                    shape = CircleShape
                )
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Alexandra Monroe",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "alexandramonroe@mail.com",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader()
}


