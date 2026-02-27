package com.zinab.veloura2.ui.Screens.detailsScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
@Composable
fun AddToBagBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {Button(
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE76F51),
            contentColor = Color.White
        )
    ) {
        Text(text = "Add to Bag")
    }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
@Composable
fun AddToBagBarPreview() {
    AddToBagBar()
}