package com.zinab.veloura2.ui.Screens.onBoarding
import android.R.attr.shape
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.zinab.veloura2.R




/* =======================
   DATA MODEL
======================= */
data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String
)

/* =======================
   VIEWMODEL (MVVM)
======================= */
class OnboardingViewModel : ViewModel() {

    val pages = listOf(
        OnboardingPage(
            image = R.drawable.onboarding1,
            title = "Explore Handpicked Fashion",
            description = "Explore our luxurious fashion collection designed to make you look elegant."
        ),
        OnboardingPage(
            image = R.drawable.onboarding2,
            title = "Find Your Unique Style",
            description = "Get personalized recommendations just for you."
        ),
        OnboardingPage(
            image = R.drawable.onboarding3,
            title = "Ready To Shop With Elegant Touch",
            description = "Easy, secure, and fast shopping experience."
        )
    )
}



/* =======================
   MAIN SCREEN
======================= */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = OnboardingViewModel(),
    onFinish: () -> Unit   // ✅ لازم
) {
    val pagerState = rememberPagerState { viewModel.pages.size }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingItem(viewModel.pages[page])
        }

        Button(
            onClick = {
                if (pagerState.currentPage < viewModel.pages.size - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1
                        )
                    }
                } else {
                    // ✅ آخر صفحة
                    onFinish()
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .height(56.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE76F51)
            )
        ) {
            Text(
                text = if (pagerState.currentPage == viewModel.pages.size - 1)
                    "Get Started" else "Next",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* =======================
   SINGLE PAGE UI
======================= */
@Composable
fun OnboardingItem(page: OnboardingPage) {

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = page.title,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = page.description,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}


/* =======================
   PREVIEW – SCREEN 1
======================= */
@Preview(showBackground = true)
@Composable
fun OnboardingPreview1() {
    OnboardingItem(
        page = OnboardingPage(
            image = R.drawable.onboarding1,
            title = "Explore Handpicked Fashion",
            description = "Explore our luxurious fashion collection designed to make you look elegant."
        )
    )
}

/* =======================
   PREVIEW – SCREEN 2
======================= */
@Preview(showBackground = true)
@Composable
fun OnboardingPreview2() {
    OnboardingItem(
        page = OnboardingPage(
            image = R.drawable.onboarding2,
            title = "Find Your Unique Style",
            description = "Get personalized recommendations just for you."
        )
    )
}

/* =======================
   PREVIEW – SCREEN 3
======================= */
@Preview(showBackground = true)
@Composable
fun OnboardingPreview3() {
    OnboardingItem(
        page = OnboardingPage(
            image = R.drawable.onboarding3,
            title = "Shop With Confidence",
            description = "Easy, secure, and fast shopping experience."
        )
    )
}