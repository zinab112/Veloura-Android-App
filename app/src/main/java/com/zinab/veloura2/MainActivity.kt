package com.zinab.veloura2

import android.app.Activity
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import com.zinab.veloura2.navigation.MainNavHost
//import com.zinab.veloura2.navigation.MainNavHost
import com.zinab.veloura2.ui.Screens.homeScreen.HomeScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val view = LocalView.current
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                window.navigationBarColor = android.graphics.Color.TRANSPARENT

            }
                MainNavHost()

        }
}}
