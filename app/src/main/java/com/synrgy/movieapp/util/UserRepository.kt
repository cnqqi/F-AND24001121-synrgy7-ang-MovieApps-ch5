package com.synrgy.movieapp.util

import com.synrgy.movieapp.database.UserDao
import com.synrgy.movieapp.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun getUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
}
