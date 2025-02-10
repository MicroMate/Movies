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

    private val _responseState =
        MutableStateFlow<ResponseState<List<MovieModel>>>(ResponseState.Success(emptyList()))
    val responseState: StateFlow<ResponseState<List<MovieModel>>> = _responseState
    private val _isLoading = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> = _isLoading
    private val _movie = MutableStateFlow<MovieModel?>(null)
    val movie: StateFlow<MovieModel?> = _movie.asStateFlow()

    private val _topBarState = MutableStateFlow(TopBarState.NOW_PLAYING)
    val topBarState: StateFlow<TopBarState> = _topBarState

    init {
        loadMovies()
    }

    fun setTopBarState(state: TopBarState) {
        _topBarState.value = state
    }

    fun loadMovies() {
        if (isFetching || currentPage > totalPages) return

        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {

            _isLoading.value = true
            isFetching = true

            try {
                val response = apiService.fetchNowPlayingMovies(AppConfig.API_KEY, currentPage)
                totalPages = response.totalPages

                val favoriteIds = sharedPreferencesHelper.getFavoriteMovies()
                val mappedMovies = response.results.map { movie ->
                    movie.copy(isFavorite = favoriteIds.contains(movie.id))
                }

                val updatedMovies = when (val currentState = _responseState.value) {

                    is ResponseState.Success -> {
                        currentState.data + mappedMovies
                    }

                    else -> {
                        mappedMovies
                    }
                }

                _responseState.value = ResponseState.Success(updatedMovies)

                currentPage++

            } catch (e: Exception) {
                _responseState.value = ResponseState.Error(e.message ?: "Unknown error")
            } finally {
                isFetching = false
                _isLoading.value = false
            }
        }
    }


    fun selectMovie(movie: MovieModel) {
        _movie.value = movie
    }


    fun toggleFavorite(movie: MovieModel) {
        val currentState = _responseState.value

        if (currentState is ResponseState.Success) {
            val updatedMovies = currentState.data.map {
                if (it.id == movie.id) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    it
                }
            }

            val favoriteIds = updatedMovies.filter { it.isFavorite }.map { it.id }.toSet()
            sharedPreferencesHelper.saveFavoriteMovies(favoriteIds)

            _responseState.value = ResponseState.Success(updatedMovies)
        }

        _movie.value = _movie.value?.let { it.copy(isFavorite = !it.isFavorite) }
    }

}