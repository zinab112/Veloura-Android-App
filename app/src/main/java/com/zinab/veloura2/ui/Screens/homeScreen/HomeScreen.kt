package com.zinab.veloura2.ui.Screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.zinab.veloura2.R
import com.zinab.veloura2.domain.mapper.toUiModel
import com.zinab.veloura2.ui.Screens.homeScreen.viewmodel.HomeViewModel
import com.zinab.veloura2.ui.components.*

@Composable
fun HomeScreen(
    onProductClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val currentProducts by viewModel.currentProducts.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1A16)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


            item { HomeHeader(user = User("Zinab", R.drawable.person)) }

            item { SearchSection() }

            item { PromoBannerRow() }

            item { SectionHeader(title = "Categories") }
            item {
                CategoryRow(
                    categories = fakeCategories,
                    selectedCategoryId = selectedCategory?.id ?: 0, // All Item افتراضياً
                    onCategoryClick = { category ->
                        viewModel.selectCategory(category) // 👈 هنا بيجيب المنتجات من API
                    }
                )
            }

            item { SectionHeader(title = "Mood") }
            item { MoodSectionGrid() }

            item { SectionHeader(title = "New Arrivals") }
            item {
                ProductGridHorizontal(
                    products =  currentProducts.map { it.toUiModel() }, // 👈 هنا المنتجات الحالية (بتتغير حسب الكاتيجوري)
                    onProductClick = onProductClick
                )
            }

            item { SectionHeader(title = "Promos for You") }
            item {
                ProductGridHorizontal(
                    products =  currentProducts.map { it.toUiModel() },
                    onProductClick = onProductClick
                )
            }
        }
    }
