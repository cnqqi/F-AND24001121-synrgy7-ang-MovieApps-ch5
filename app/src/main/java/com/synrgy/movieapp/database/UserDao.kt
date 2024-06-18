package com.synrgy.movieapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.synrgy.movieapp.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?

    @Insert
    suspend fun insertUser(user: User)
}