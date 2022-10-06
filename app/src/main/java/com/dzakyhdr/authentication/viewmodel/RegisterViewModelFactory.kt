package com.dzakyhdr.authentication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.authentication.data.Injection
import com.dzakyhdr.authentication.data.UserRepository

class RegisterViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: RegisterViewModelFactory? = null

        fun getInstance(context: Context): RegisterViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: RegisterViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }

}