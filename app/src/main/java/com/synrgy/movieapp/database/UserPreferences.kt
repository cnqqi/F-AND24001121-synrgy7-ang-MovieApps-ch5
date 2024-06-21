package com.synrgy.movieapp.database

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_USERNAME = "username"
        private const val KEY_FULL_NAME = "full_name"
        private const val KEY_DATE_OF_BIRTH = "date_of_birth"
        private const val KEY_ADDRESS = "address"
        private const val KEY_PROFILE_IMAGE_URL = "profile_image_url"
    }

    var username: String?
        get() = preferences.getString(KEY_USERNAME, null)
        set(value) = preferences.edit().putString(KEY_USERNAME, value).apply()

    var fullName: String?
        get() = preferences.getString(KEY_FULL_NAME, null)
        set(value) = preferences.edit().putString(KEY_FULL_NAME, value).apply()

    var dateOfBirth: String?
        get() = preferences.getString(KEY_DATE_OF_BIRTH, null)
        set(value) = preferences.edit().putString(KEY_DATE_OF_BIRTH, value).apply()

    var address: String?
        get() = preferences.getString(KEY_ADDRESS, null)
        set(value) = preferences.edit().putString(KEY_ADDRESS, value).apply()

    var profileImageUrl: String?
        get() = preferences.getString(KEY_PROFILE_IMAGE_URL, null)
        set(value) = preferences.edit().putString(KEY_PROFILE_IMAGE_URL, value).apply()
}