package com.zinab.veloura2.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zinab.veloura2.ui.Screens.detailsScreen.DetailsScreen


import com.zinab.veloura2.ui.Screens.splashScreen.SplashScreen
import com.zinab.veloura2.ui.Screens.onBoarding.OnboardingScreen
import com.zinab.veloura2.ui.Screens.signInSignUpScreen.SignInSignUp
import com.zinab.veloura2.ui.Screens.signinScreen.SignInScreen
import com.zinab.veloura2.ui.Screens.signupScreen.SignUpScreen
import com.zinab.veloura2.ui.Screens.homeScreen.HomeScreen

import com.zinab.veloura2.ui.Screens.profile.ProfileScreen
import com.zinab.veloura2.ui.Screens.shoppingBag.CartScreen
import com.zinab.veloura2.ui.components.ProfileBottomBarUI



@Composable
fun MainNavHost() {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1A16)),
        contentWindowInsets = WindowInsets(0, 0, 0, 0), // 👈 مهم
        bottomBar = {
            if (route == NavRoutes.Home || route == NavRoutes.Profile ||  route == NavRoutes.CartScreen){
                ProfileBottomBarUI(
                    selectedRoute = route ?: NavRoutes.Home,
                    navController = navController
                )
            }
        }
    )
     { padding ->

        NavHost(
            navController = navController,
            startDestination = NavRoutes.Splash,
            modifier = Modifier.padding(padding)
        ) {

            composable(NavRoutes.Splash) {
                SplashScreen {
                    navController.navigate(NavRoutes.OnBoarding) {
                        popUpTo(NavRoutes.Splash) { inclusive = true }
                    }
                }
            }

            composable(NavRoutes.OnBoarding) {
                OnboardingScreen {
                    navController.navigate(NavRoutes.SigninSignup) {
                        popUpTo(NavRoutes.OnBoarding) { inclusive = true }
                    }
                }
            }

            composable(NavRoutes.SigninSignup) {
                SignInSignUp(
                    onSignInClick = { navController.navigate(NavRoutes.Login) },
                    onSignUpClick = { navController.navigate(NavRoutes.SignUp) }
                )
            }

            composable(NavRoutes.Login) {
                SignInScreen(
                    onSignInClick = {
                        navController.navigate(NavRoutes.Home) {
                            popUpTo(NavRoutes.SigninSignup) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onSignUpClick = {
                        navController.navigate(NavRoutes.SignUp)
                    }
                )
            }

            composable(NavRoutes.SignUp) {
                SignUpScreen {
                    navController.navigate(NavRoutes.Home) {
                        popUpTo(NavRoutes.SigninSignup) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
            composable(NavRoutes.CartScreen) {  // <-- استخدمي NavRoutes مباشرة
                CartScreen(
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { productId ->
                        navController.navigate("${NavRoutes.Details}/$productId")  // <-- نفس طريقة HomeScreen
                    }
                )
            }

            composable(NavRoutes.Home) {
                HomeScreen(
                    onProductClick = { productId ->
                        navController.navigate("${NavRoutes.Details}/$productId")
                    },

                )
            }
// استورد هذه المكتبات في أعلى


            composable(
                route = "${NavRoutes.Details}/{productId}",
                // 1. قم بتعريف الـ argument ونوعه بشكل صريح
                arguments = listOf(navArgument("productId") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                // 2. استخدم getInt لاستعادة الرقم بأمان
                val productId = backStackEntry.arguments?.getInt("productId") ?: 0 // يمكنك استخدام القيمة الافتراضية
                DetailsScreen(
                    productId = productId
                )
            }


            composable(NavRoutes.Profile) {
                ProfileScreen(navController)
            }
        }
    }
}
