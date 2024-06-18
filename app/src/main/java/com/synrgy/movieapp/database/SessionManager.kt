package com.synrgy.movieapp.database

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "user_session",
        Context.MODE_PRIVATE
    )

    fun saveSession(username: String) {
        sharedPreferences.edit().putString("username", username).apply()
    }

    fun fetchUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}