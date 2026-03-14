package com.zinab.veloura2.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zinab.veloura2.navigation.NavRoutes

data class BottomItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

val bottomItems = listOf(
    BottomItem("Home", Icons.Outlined.Home, NavRoutes.Home),
    BottomItem("Search", Icons.Outlined.Search, ""),
    BottomItem("Wishlist", Icons.Outlined.FavoriteBorder, ""),
    BottomItem("Cart", Icons.Outlined.ShoppingBag, NavRoutes.CartScreen),
    BottomItem("Profile", Icons.Outlined.Person, NavRoutes.Profile)
)




@Composable
fun ProfileBottomBarUI(
    selectedRoute: String,
    navController: NavController
) {

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFF2C2924))
            .padding(horizontal = 16.dp)
    ) {
        bottomItems.forEach { item ->
            if (item.route == selectedRoute) {
                ActiveItem(item)
            } else {
                InactiveItem(item) {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
}



@Composable
fun ActiveItem(item: BottomItem) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(item.icon, contentDescription = null, tint = Color.Black)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = item.title,
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun InactiveItem(
    item: BottomItem,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            item.icon,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}


