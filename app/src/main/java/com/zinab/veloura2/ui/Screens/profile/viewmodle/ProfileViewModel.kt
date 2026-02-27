package com.zinab.veloura.ui.Screens.profile.viewmodle


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    // Dark Mode switch
    var isDarkMode: MutableState<Boolean> = mutableStateOf(false)
        private set

    fun toggleDarkMode() {
        isDarkMode.value = !isDarkMode.value
    }

    // ممكن تضيفي أي state تانية هنا بعدين
    // مثال: Orders, Settings, Profile info
}
