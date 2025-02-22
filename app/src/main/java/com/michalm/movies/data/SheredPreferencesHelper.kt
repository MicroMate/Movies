package com.michalm.movies.data

import android.content.Context
import android.content.SharedPreferences


class SharedPreferencesHelper(context: Context) {

    companion object {
        private const val PREFS_FILE_NAME = "movies"
        private const val KEY_FAVORITE_MOVIES = "favorite_movies"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

    private fun saveFavoriteMovies(favoriteMovieIds: Set<Int>) {

        val stringSet = favoriteMovieIds.map { it.toString() }.toSet()

        sharedPreferences.edit().apply {
            putStringSet(KEY_FAVORITE_MOVIES, stringSet)
            apply()
        }
    }

    fun getFavoriteMovies(): Set<Int> {
        val stringSet =
            sharedPreferences.getStringSet(KEY_FAVORITE_MOVIES, emptySet()) ?: emptySet()
        return stringSet.mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun isFavoriteMovie(movieId: Int): Boolean {
        return getFavoriteMovies().contains(movieId)
    }

    fun toggleFavoriteMovie(movieId: Int) {
        val favoriteMovies = getFavoriteMovies().toMutableSet()
        if (favoriteMovies.contains(movieId)) {
            favoriteMovies.remove(movieId)
        } else {
            favoriteMovies.add(movieId)
        }
        saveFavoriteMovies(favoriteMovies)
    }
}