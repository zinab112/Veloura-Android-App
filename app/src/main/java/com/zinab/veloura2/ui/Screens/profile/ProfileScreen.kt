package com.zinab.veloura2.ui.Screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

import com.zinab.veloura.ui.Screens.profile.viewmodle.ProfileViewModel
import com.zinab.veloura2.ui.components.ProfileBottomBarUI

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {

    Scaffold(
        bottomBar = {
            ProfileBottomBarUI(
                selectedRoute = navController.currentBackStackEntry?.destination?.route ?: "profile",
                navController = navController
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(padding) // مهم: padding من Scaffold
        ) {
            item { ProfileHeader() }
            item { OrdersSection() }
            item {
                SettingsItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Mode",
                    trailing = {
                        Switch(
                            checked = viewModel.isDarkMode.value,
                            onCheckedChange = { viewModel.toggleDarkMode() }
                        )
                    }
                )
            }
        }
    }
}
