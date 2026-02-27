//package com.zinab.veloura2.ui.Screens.detailsScreen
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun ShippingInfoSection(quantity: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "Shipping Information", fontWeight = FontWeight.Medium)
//        Spacer(modifier = Modifier.height(4.dp))
//
//        Row {
//            Button(onClick = onDecrease) { Text("-") }
//            Text(text = quantity.toString(), modifier = Modifier.padding(horizontal = 16.dp))
//            Button(onClick = onIncrease) { Text("+") }
//        }
//
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(text = "• Shipping to United States")
//        Text(text = "• Free returns within 30 days")
//    }
//}
