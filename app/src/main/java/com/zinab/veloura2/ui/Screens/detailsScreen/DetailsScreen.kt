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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.zinab.veloura2.doman.model.ProductDomain
import com.zinab.veloura2.doman.model.Review

// تعريف ألوان مخصصة لتتناسب مع الخلفية الداكنة
private val DarkBackground = Color(0xFF1B1A16) // الخلفية المطلوبة
private val WhiteText = Color.White // للعناوين
private val LightGrayText = Color.LightGray // للوصف والتفاصيل
private val GrayText = Color.Gray // للنصوص الثانوية
private val DividerColor = Color.DarkGray // لون فواصل أفتح قليلاً من الخلفية

@Composable
fun DetailsScreen(
    productId: Int,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    val product: ProductDomain? = viewModel.product.value
    var expandedSection by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = DarkBackground // تعيين لون الخلفية للشاشة بالكامل
    ) { padding ->
        if (product == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = WhiteText) // جعل لون الانتظار أبيض
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(DarkBackground) // تأكيد الخلفية للـ LazyColumn
            ) {
                item { ProductImageSection(product.images) }
                item { ProductHeaderSection(product) }
                item { SizeSelectorSection(product.sizes) }
                item { ShippingInfoSection() }
                item {
                    ExpandableInfoSection(
                        title = "Product Description",
                        content = product.description,
                        isExpanded = expandedSection == "description",
                        onToggle = { expandedSection = if (expandedSection == "description") null else "description" }
                    )
                }
                item {
                    ExpandableInfoSection(
                        title = "Key Features",
                        content = product.features.joinToString(separator = "\n") { "• $it" },
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
                item { ReviewsSection(reviews = product.reviews) }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

// ---------------------- Updated Composables ----------------------

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

@Composable
fun ProductHeaderSection(product: ProductDomain) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Price and Rating Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$${product.price}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhiteText // لون النص الأساسي
                )
                Text(
                    text = " $${product.price}",
                    fontSize = 18.sp,
                    color = GrayText, // لون ثانوي للنص المشطوب
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Rating
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "4.7",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGrayText // لون فاتح للوصف
                )
                Text(
                    text = " (19 reviews)",
                    fontSize = 14.sp,
                    color = GrayText, // لون ثانوي
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
            color = WhiteText // العنوان الرئيسي
        )
        Text(
            text = "by Velvosa Atelier",
            fontSize = 16.sp,
            color = LightGrayText, // لون فاتح للوصف
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Size Label
        Text(
            text = "Size",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = WhiteText // العنوان
        )
    }
}

@Composable
fun SizeSelectorSection(sizes: List<String>) {
    val displaySizes = if (sizes.isEmpty()) listOf("XS", "S", "M", "L", "XL") else sizes

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        displaySizes.take(3).forEach { size ->
            OutlinedButton(
                onClick = { /* Handle size selection */ },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, GrayText)

            ) {
                Text(text = size)
            }
        }
    }
}

@Composable
fun ShippingInfoSection() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // International Shipping
        Text(
            text = "Shipping to United States",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = WhiteText // العنوان
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "• Free international shipping over $50",
            fontSize = 14.sp,
            color = LightGrayText, // الوصف
        )

        Text(
            text = "• Estimated shipping time: 5 - 7 business days",
            fontSize = 14.sp,
            color = LightGrayText, // الوصف
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

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
        Divider(color = DividerColor, thickness = 1.dp) // فاصل بلون داكن

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
                color = WhiteText // العنوان
            )

            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = LightGrayText // لون الأيقونة
            )
        }

        if (isExpanded) {
            Text(
                text = content,
                fontSize = 14.sp,
                color = LightGrayText, // الوصف بالرمادي الفاتح
                modifier = Modifier.padding(bottom = 16.dp),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun ReviewsSection(reviews: List<Review>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Divider(color = DividerColor, thickness = 1.dp) // فاصل بلون داكن

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
                    color = WhiteText // العنوان
                )
                Text(
                    text = " (212)",
                    fontSize = 14.sp,
                    color = GrayText, // لون ثانوي
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "4.7",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGrayText // الوصف
                )
                Text(
                    text = " ★★★★★",
                    fontSize = 16.sp,
                    color = Color(0xFFFFC107), // لون النجوم يبقى كما هو
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        if (reviews.isEmpty()) {
            // Show sample review from image
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DividerColor // لون خلفية البطاقة أفتح قليلاً من الخلفية الرئيسية
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "H***1",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = WhiteText // العنوان
                        )
                        Text(
                            text = "⭐⭐⭐⭐⭐",
                            fontSize = 14.sp,
                            color = Color(0xFFFFC107)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "This coat is super warm and comfortable. The gold-plated buttons add a touch of luxury and elegance. I love it!",
                        fontSize = 14.sp,
                        color = LightGrayText, // الوصف
                        lineHeight = 20.sp
                    )
                }
            }
        } else {
            reviews.forEach { review ->
                ReviewItem(review = review)
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DividerColor // لون خلفية البطاقة
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.reviewerName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = WhiteText // العنوان
                )
                Text(
                    text = "⭐".repeat(review.rating),
                    fontSize = 14.sp,
                    color = Color(0xFFFFC107)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = review.comment,
                fontSize = 14.sp,
                color = LightGrayText, // الوصف
                lineHeight = 20.sp
            )
        }
    }
}