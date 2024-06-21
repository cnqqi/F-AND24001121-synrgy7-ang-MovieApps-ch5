package com.synrgy.movieapp.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.synrgy.movieapp.model.Movie

object FavoritesRepository {

    private const val PREFS_NAME = "favorites_prefs"
    private const val FAVORITES_KEY = "favorite_movies"

    fun addFavoriteMovie(context: Context, movie: Movie) {
        val favorites = getFavoriteMovies(context).toMutableList()
        favorites.add(movie)
        saveFavoriteMovies(context, favorites)
    }

    fun getFavoriteMovies(context: Context): List<Movie> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(FAVORITES_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<List<Movie>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun saveFavoriteMovies(context: Context, movies: List<Movie>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = Gson().toJson(movies)
        editor.putString(FAVORITES_KEY, json)
        editor.apply()
    }
}
