package com.zinab.veloura2.ui.Screens.detailsScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.zinab.veloura2.doman.model.ProductDomain
import com.zinab.veloura2.doman.model.Review

// تعريف ألوان مخصصة
private val DarkBackground = Color(0xFF1B1A16)
private val WhiteText = Color.White
private val LightGrayText = Color.LightGray
private val GrayText = Color.Gray
private val DividerColor = Color.DarkGray
private val GoldColor = Color(0xFFC6A43C)

@Composable
fun DetailsScreen(
    productId: Int,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    // ✅ قراءة القيم من ViewModel
    val product by viewModel.product
    val selectedSize by viewModel.selectedSize
    val selectedColor by viewModel.selectedColor
    val quantity by viewModel.quantity
    val isLoved by viewModel.isLoved
    val isSaved by viewModel.isSaved

    var expandedSection by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            if (product != null) {
                AddToBagBar(
                    onAddToBagClick = viewModel::addToCart
                )
            }
        }
    ) { padding ->
        if (product == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = WhiteText)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(DarkBackground)
            ) {
                item { ProductImageSection(product!!.images) }
                item {
                    ProductHeaderSection(
                        product = product!!,
                        selectedColor = selectedColor,
                        onColorSelected = viewModel::selectColor
                    )
                }

                // Love & Save Section
                item {
                    LoveAndSaveSection(
                        isLoved = isLoved,
                        isSaved = isSaved,
                        onLoveClick = viewModel::toggleLove,
                        onSaveClick = viewModel::toggleSave
                    )
                }

                item {
                    SizeSelectorSection(
                        sizes = product!!.sizes,
                        selectedSize = selectedSize,
                        onSizeSelected = viewModel::selectSize
                    )
                }
                item { ShippingInfoSection() }
                item {
                    ExpandableInfoSection(
                        title = "Product Description",
                        content = product!!.description,
                        isExpanded = expandedSection == "description",
                        onToggle = { expandedSection = if (expandedSection == "description") null else "description" }
                    )
                }
                item {
                    ExpandableInfoSection(
                        title = "Key Features",
                        content = product!!.features.joinToString(separator = "\n") { "• $it" },
                        isExpanded = expandedSection == "features",
                        onToggle = { expandedSection = if (expandedSection == "features") null else "features" }
                    )
                }
                item {
                    ExpandableInfoSection(
                        title = "Styling Suggestion",
                        content = "• Pants\n• V-neckline & bootie\n• Structured mini bag\n• Silk insert too",
                        isExpanded = expandedSection == "styling",
                        onToggle = { expandedSection = if (expandedSection == "styling") null else "styling" }
                    )
                }
                item { ReviewsSection(reviews = product!!.reviews) }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

// ---------------------- ProductHeaderSection ----------------------
@Composable
fun ProductHeaderSection(
    product: ProductDomain,
    selectedColor: String?,
    onColorSelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Price and Rating Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$${product.price}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = WhiteText
            )

            // Rating
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "4.7",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGrayText
                )
                Text(
                    text = " (19 reviews)",
                    fontSize = 14.sp,
                    color = GrayText,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Product Title and Brand
        Text(
            text = product.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = WhiteText
        )
        Text(
            text = "by Velvosa Atelier",
            fontSize = 16.sp,
            color = LightGrayText,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Colors Section
        if (product.colors.isNotEmpty()) {
            Text(
                text = "Color",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = WhiteText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                product.colors.forEach { color ->
                    ColorCircle(
                        color = color,
                        isSelected = color == selectedColor,
                        onClick = { onColorSelected(color) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Size Label
        Text(
            text = "Size",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = WhiteText
        )
    }
}

// ---------------------- ColorCircle ----------------------
@Composable
fun ColorCircle(
    color: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when (color.lowercase()) {
        "red" -> Color.Red
        "blue" -> Color.Blue
        "black" -> Color.Black
        "white" -> Color.White
        "gold" -> GoldColor
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) GoldColor else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() }
    )
}

// ---------------------- SizeSelectorSection ----------------------
@Composable
fun SizeSelectorSection(
    sizes: List<String>,
    selectedSize: String?,
    onSizeSelected: (String) -> Unit
) {
    val displaySizes = if (sizes.isEmpty()) listOf("XS", "S", "M", "L", "XL") else sizes

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        displaySizes.forEach { size ->
            val isSelected = size == selectedSize

            OutlinedButton(
                onClick = { onSizeSelected(size) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = if (size != displaySizes.last()) 8.dp else 0.dp),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) GoldColor else GrayText
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = if (isSelected) GoldColor else LightGrayText
                )
            ) {
                Text(text = size)
            }
        }
    }
}

// ---------------------- ProductImageSection ----------------------
@Composable
fun ProductImageSection(images: List<String>) {
    if (images.isNotEmpty()) {
        Image(
            painter = rememberAsyncImagePainter(images[0]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentScale = ContentScale.Crop
        )
    }
}

// ---------------------- ShippingInfoSection ----------------------
@Composable
fun ShippingInfoSection() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Shipping to United States",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = WhiteText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "• Free international shipping over $50",
            fontSize = 14.sp,
            color = LightGrayText,
        )

        Text(
            text = "• Estimated shipping time: 5 - 7 business days",
            fontSize = 14.sp,
            color = LightGrayText,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// ---------------------- ExpandableInfoSection ----------------------
@Composable
fun ExpandableInfoSection(
    title: String,
    content: String,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Divider(color = DividerColor, thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable { onToggle() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = WhiteText
            )

            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = LightGrayText
            )
        }

        if (isExpanded) {
            Text(
                text = content,
                fontSize = 14.sp,
                color = LightGrayText,
                modifier = Modifier.padding(bottom = 16.dp),
                lineHeight = 20.sp
            )
        }
    }
}

// ---------------------- ReviewsSection ----------------------
@Composable
fun ReviewsSection(reviews: List<Review>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Divider(color = DividerColor, thickness = 1.dp)

        // Reviews Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Reviews",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = WhiteText
                )
                Text(
                    text = " (${reviews.size})",
                    fontSize = 14.sp,
                    color = GrayText,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "4.7",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGrayText
                )
                Text(
                    text = " ★★★★★",
                    fontSize = 16.sp,
                    color = Color(0xFFFFC107),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        if (reviews.isEmpty()) {
            // Show sample review
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DividerColor
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "H***1",
                            fontWeight = FontWeight.Bold,
                            color = WhiteText
                        )
                        Text(
                            text = "⭐⭐⭐⭐⭐",
                            color = Color(0xFFFFC107)
                        )
                    }
                    Text(
                        text = "This coat is super warm and comfortable. The gold-plated buttons add a touch of luxury and elegance. I love it!",
                        color = LightGrayText,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        } else {
            reviews.forEach { review ->
                ReviewItem(review)
            }
        }
    }
}

// ---------------------- ReviewItem ----------------------
@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DividerColor
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = review.reviewerName,
                    fontWeight = FontWeight.Bold,
                    color = WhiteText
                )
                Text(
                    text = "⭐".repeat(review.rating),
                    color = Color(0xFFFFC107)
                )
            }
            Text(
                text = review.comment,
                color = LightGrayText,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

// ---------------------- LoveAndSaveSection ----------------------
@Composable
fun LoveAndSaveSection(
    isLoved: Boolean,
    isSaved: Boolean,
    onLoveClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = DividerColor.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Love Button
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onLoveClick() }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isLoved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Love",
                    tint = if (isLoved) Color.Red else LightGrayText,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isLoved) "Loved" else "Love",
                    color = if (isLoved) Color.Red else LightGrayText,
                    fontSize = 14.sp
                )
            }

            // Divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(GrayText)
            )

            // Save Button
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSaveClick() }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Save",
                    tint = if (isSaved) GoldColor else LightGrayText,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isSaved) "Saved" else "Save",
                    color = if (isSaved) GoldColor else LightGrayText,
                    fontSize = 14.sp
                )
            }
        }
    }
}

// ---------------------- AddToBagBar ----------------------
@Composable
fun AddToBagBar(
    onAddToBagClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = onAddToBagClick,
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