package com.zinab.veloura2.ui.Screens.homeScreen

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.android.parcel.Parcelize

// ------------------ Data ------------------

// ✅ إزالة Parcelize من ImageVector لأنها مش Parcelable
data class Category(
    val id: Int,
    val name: String,
    val icon: ImageVector, // مش هتكون Parcelable
    val apiCategory: String? = null
) // مش هنستخدم Parcelize عشان فيه ImageVector

// ✅ لو محتاجة Parcelable للـ Navigation، نعمل نسخة بدون ImageVector
@Parcelize
data class CategoryParcelable(
    val id: Int,
    val name: String,
    val apiCategory: String? = null
) : Parcelable

// تحويل Category العادي إلى CategoryParcelable
fun Category.toParcelable() = CategoryParcelable(id, name, apiCategory)

val fakeCategories = listOf(
    Category(0, "All Item", Icons.Filled.Favorite, null),
    Category(1, "Dresses", Icons.Filled.Favorite, "womens-dresses"),
    Category(2, "Shoes", Icons.Filled.ShoppingCart, "womens-shoes"),
    Category(3, "Bags", Icons.Filled.Favorite, "womens-bags"),
    Category(4, "Accessories", Icons.Filled.Favorite, "womens-jewellery"),
    Category(5, "Watches", Icons.Filled.Favorite, "womens-watches")
)

// ------------------ Row ------------------
@Composable
fun CategoryRow(
    categories: List<Category>,
    selectedCategoryId: Int = 0,
    onCategoryClick: (Category) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = selectedCategoryId == category.id,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

// ------------------ Item ------------------
@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val selectedColor = Color(0xFFE76F51)
    val borderColor = if (isSelected) selectedColor else Color.White.copy(alpha = 0.3f)

    Row(
        modifier = Modifier
            .height(40.dp)
            .clickable { onClick() }
            .background(
                color = if (isSelected) selectedColor else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = category.icon,
            contentDescription = category.name,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = category.name,
            color = Color.White,
            fontSize = 13.sp,
            maxLines = 1
        )
    }
}