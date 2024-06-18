package com.synrgy.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.movieapp.model.User
import com.synrgy.movieapp.util.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun login(username: String, password: String): User? {
        return userRepository.getUser(username, password)
    }

    suspend fun register(user: User) {
        userRepository.insertUser(user)
    }
}

