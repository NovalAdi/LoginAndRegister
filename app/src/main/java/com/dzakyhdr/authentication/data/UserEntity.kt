package com.dzakyhdr.authentication.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val username: String,
    val password: String
) : Parcelable
