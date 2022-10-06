package com.dzakyhdr.authentication.data

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: UserEntity) {
        userDao.insert(user)
    }

    suspend fun readLogin(username: String, password: String): UserEntity {
        return userDao.readLogin(username, password)
    }

    suspend fun getUser(id: Int): UserEntity {
        return userDao.getUser(id)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(userDao: UserDao): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao)
            }.also { instance = it }
    }
}