package com.michalm.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michalm.movies.data.SharedPreferencesHelper
import com.michalm.movies.network.ApiService
import com.michalm.movies.network.MovieModel
import com.michalm.movies.utils.AppConfig
import com.michalm.movies.utils.ResponseState
import com.michalm.movies.utils.TopBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
) : ViewModel() {

    private var currentPage = 1
    private var totalPages = 1
    private var isFetching = false

    private val _responseState = MutableStateFlow<ResponseState>(ResponseState.Loading)
    val responseState: StateFlow<ResponseState> = _responseState

    private val _movieListResult = MutableStateFlow<List<MovieModel>>(emptyList())
    val movieListResult: StateFlow<List<MovieModel>> = _movieListResult

    private val _movie = MutableStateFlow<MovieModel?>(null)
    val movie: StateFlow<MovieModel?> = _movie.asStateFlow()

    private val _topBarState = MutableStateFlow(TopBarState.NOW_PLAYING)
    val topBarState: StateFlow<TopBarState> = _topBarState

    private val _searchResults = MutableStateFlow<List<MovieModel>>(emptyList())
    val searchResults: StateFlow<List<MovieModel>> = _searchResults

    fun setTopBarState(state: TopBarState) {
        _topBarState.value = state
    }

    fun loadMovies() {
        if (isFetching || currentPage > totalPages) return

        fetchMovies()
    }

    private fun fetchMovies() {

        viewModelScope.launch {

            _responseState.value = ResponseState.Loading
            isFetching = true

            try {
                val response = apiService.fetchNowPlayingMovies(AppConfig.API_KEY, currentPage)
                totalPages = response.totalPages

                val favoriteIds = sharedPreferencesHelper.getFavoriteMovies()
                val mappedMovies = response.results.map { movie ->
                    movie.copy(isFavorite = favoriteIds.contains(movie.id))
                }

                _movieListResult.value += mappedMovies
                _responseState.value = ResponseState.Success
                currentPage++

            } catch (e: Exception) {
                _responseState.value = ResponseState.Error(e.message ?: "Unknown error")
            } finally {
                isFetching = false
            }
        }
    }

    fun selectMovie(movie: MovieModel) {
        _movie.value = movie
    }

    fun toggleFavorite(movie: MovieModel) {

        val updatedMovies = _movieListResult.value.map {
            if (it.id == movie.id) {
                it.copy(isFavorite = !it.isFavorite)
            } else {
                it
            }
        }
        val favoriteIds = updatedMovies.filter { it.isFavorite }.map { it.id }.toSet()
        sharedPreferencesHelper.saveFavoriteMovies(favoriteIds)

        _movieListResult.value = updatedMovies

        _movie.value = _movie.value?.let { it.copy(isFavorite = !it.isFavorite) }
    }

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        //TODO: handle response states
        viewModelScope.launch {
            try {
                val response = apiService.searchMovies(AppConfig.API_KEY, query)

                val favoriteIds = sharedPreferencesHelper.getFavoriteMovies()

                _searchResults.value = response.results.map { movie ->
                    movie.copy(isFavorite = favoriteIds.contains(movie.id))
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}