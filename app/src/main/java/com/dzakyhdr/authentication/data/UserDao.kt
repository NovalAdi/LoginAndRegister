package com.dzakyhdr.authentication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE username LIKE :username AND password LIKE :password")
    suspend fun readLogin(username: String, password: String): UserEntity

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUser(id: Int): UserEntity
}