package com.dzakyhdr.authentication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.authentication.UserDataStoreManager
import com.dzakyhdr.authentication.data.Injection
import com.dzakyhdr.authentication.data.UserRepository

class LoginViewModelFactory(
    private val repository: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: LoginViewModelFactory? = null

        fun getInstance(
            context: Context,
            userDataStoreManager: UserDataStoreManager
        ): LoginViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: LoginViewModelFactory(
                    Injection.provideRepository(context),
                    userDataStoreManager
                )
            }.also { instance = it }
        }
    }
}
