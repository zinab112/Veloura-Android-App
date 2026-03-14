package com.zinab.veloura2.ui.Screens.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)


    var isSuccess by mutableStateOf(false)
        private set

    fun signUp(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Please enter email and password"
            return
        }

        if (password.length < 6) {
            errorMessage = "Password must be at least 6 characters"
            return
        }

        resetState()
        isLoading = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    isSuccess = true
                } else {
                    errorMessage = task.exception?.localizedMessage ?: "Registration failed"
                }
            }
    }

    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Please enter email and password"
            return
        }

        resetState()
        isLoading = true

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    isSuccess = true
                } else {
                    errorMessage = task.exception?.localizedMessage ?: "Login failed"
                }
            }
    }

//    fun setErrorMessage(message: String) {
//        errorMessage = message
//    }

    private fun resetState() {
        errorMessage = null
        isSuccess = false
    }

    fun clearError() {
        errorMessage = null
    }
}