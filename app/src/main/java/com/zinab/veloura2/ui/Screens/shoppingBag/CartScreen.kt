package com.zinab.veloura2.ui.Screens.shoppingBag

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.zinab.veloura2.data.local.entity.CartItemEntity
import com.zinab.veloura2.ui.Screens.cart.CartViewModel

// الألوان
private val DarkBackground = Color(0xFF1B1A16)
private val WhiteText = Color.White
private val LightGrayText = Color.LightGray
private val GrayText = Color.Gray
private val DividerColor = Color.DarkGray
private val GoldColor = Color(0xFFC6A43C)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit = {},
    onProductClick: (Int) -> Unit = {},
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val totalPrice by viewModel.totalPrice.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Shopping Bag",
                        color = WhiteText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = WhiteText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground
                )
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                CartBottomBar(
                    totalPrice = totalPrice,
                    onCheckoutClick = { /* TODO: Checkout */ }
                )
            }
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            EmptyCartScreen()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(DarkBackground)
            ) {
                // Address Section
                item {
                    AddressSection()
                }

                // Cart Items
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onProductClick = { onProductClick(item.productId) },
                        onIncreaseQuantity = {
                            viewModel.increaseQuantity(item.productId, item.quantity)
                        },
                        onDecreaseQuantity = {
                            viewModel.decreaseQuantity(item.productId, item.quantity)
                        },
                        onRemove = {
                            viewModel.removeItem(item.productId)
                        }
                    )
                }

                // Spacer at the bottom
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun AddressSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DividerColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Select your address",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhiteText
                )
                Text(
                    text = "Please select your delivery address",
                    fontSize = 14.sp,
                    color = LightGrayText,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Button(
                onClick = { /* TODO: Select address */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Select",
                    color = WhiteText
                )
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItemEntity,
    onProductClick: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onProductClick() },
        colors = CardDefaults.cardColors(
            containerColor = DividerColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhiteText,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Color: ${item.color}",
                    fontSize = 14.sp,
                    color = LightGrayText
                )

                Text(
                    text = "Size: ${item.size}",
                    fontSize = 14.sp,
                    color = LightGrayText
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Price Row
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${item.price}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteText
                    )

                    if (item.oldPrice != null) {
                        Text(
                            text = " $${item.oldPrice}",
                            fontSize = 14.sp,
                            color = GrayText,
                            textDecoration = TextDecoration.LineThrough,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            // Quantity Controls
            Column(
                horizontalAlignment = Alignment.End
            ) {
                // Remove button
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = LightGrayText
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Quantity controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = GrayText,
                            shape = RoundedCornerShape(4.dp)
                        )
                ) {
                    TextButton(
                        onClick = onDecreaseQuantity,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            text = "-",
                            color = WhiteText,
                            fontSize = 16.sp
                        )
                    }

                    Text(
                        text = item.quantity.toString(),
                        color = WhiteText,
                        fontSize = 14.sp,
                        modifier = Modifier.width(24.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    TextButton(
                        onClick = onIncreaseQuantity,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            text = "+",
                            color = WhiteText,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartBottomBar(
    totalPrice: Double,
    onCheckoutClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = DarkBackground,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Total",
                    fontSize = 14.sp,
                    color = LightGrayText
                )
                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhiteText
                )
            }

            Button(
                onClick = onCheckoutClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldColor
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(140.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Checkout",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = WhiteText
                )
            }
        }
    }
}

@Composable
fun EmptyCartScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your cart is empty",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = WhiteText
            )
            Text(
                text = "Browse our products and add items to your cart",
                fontSize = 14.sp,
                color = LightGrayText,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

