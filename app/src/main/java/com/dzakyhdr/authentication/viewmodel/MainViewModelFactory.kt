package com.dzakyhdr.authentication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyhdr.authentication.UserDataStoreManager
import com.dzakyhdr.authentication.data.Injection
import com.dzakyhdr.authentication.data.UserRepository

class MainViewModelFactory(
    private val repository: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: MainViewModelFactory? = null

        fun getInstance(
            context: Context,
            userDataStoreManager: UserDataStoreManager
        ): MainViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: MainViewModelFactory(Injection.provideRepository(context), userDataStoreManager)
            }.also { instance = it }
        }
    }

}