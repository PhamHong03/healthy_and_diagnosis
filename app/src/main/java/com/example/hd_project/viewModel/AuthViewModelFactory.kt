package com.example.hd_project.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.hd_project.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class AuthViewModelFactory(
    private val googleSignInClient: GoogleSignInClient,
    private val repository: AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(googleSignInClient, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

