package com.dzakyhdr.authentication.data

import android.content.Context

object Injection {

    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getInstance(context)
        val userDao = database.userDao()
        return UserRepository.getInstance(userDao)
    }

}