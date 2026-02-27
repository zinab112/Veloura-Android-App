package com.zinab.veloura2.ui.Screens.profile
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OrdersSection() {
    Column(modifier = Modifier.padding(vertical = 24.dp)) {

        Row(
            modifier = Modifier.padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("My Orders", color = Color.White, fontWeight = FontWeight.Bold)
            Text("View Order History >", color = Color(0xFFE76F51))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OrderItem(Icons.Default.CreditCard, "To Pay")
            OrderItem(Icons.Default.LocalShipping, "To Ship")
            OrderItem(Icons.Default.Inventory, "To Receive")
            OrderItem(Icons.Default.Star, "To Rate")
        }
    }
}
@Composable
fun OrderItem(icon: ImageVector, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(title, color = Color.Gray, fontSize = 12.sp)
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF1B1A16)
@Composable
fun OrdersSectionPreview() {
    OrdersSection()
}
