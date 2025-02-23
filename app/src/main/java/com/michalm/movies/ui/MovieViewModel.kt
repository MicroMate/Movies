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
    val responseState: StateFlow<ResponseState> = _responseState.asStateFlow()

    private val _movieListResult = MutableStateFlow<List<MovieModel>>(emptyList())
    val movieListResult: StateFlow<List<MovieModel>> = _movieListResult.asStateFlow()

    private val _movie = MutableStateFlow<MovieModel?>(null)
    val movie: StateFlow<MovieModel?> = _movie.asStateFlow()

    private val _topBarState = MutableStateFlow(TopBarState.NOW_PLAYING)
    val topBarState: StateFlow<TopBarState> = _topBarState.asStateFlow()

    private val _searchResults = MutableStateFlow<List<MovieModel>>(emptyList())
    val searchResults: StateFlow<List<MovieModel>> = _searchResults.asStateFlow()

    private val _isSearch = MutableStateFlow(false)
    val isSearch: StateFlow<Boolean> = _isSearch.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

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
        if (!_movieListResult.value.contains(movie)) {
            _movie.value?.isFavorite = sharedPreferencesHelper.isFavoriteMovie(movie.id)
        }
    }

    fun toggleFavorite(movie: MovieModel, fromDetails: Boolean = false) {
        if (_movieListResult.value.contains(movie)) {
            _movieListResult.value = _movieListResult.value.map {
                if (it.id == movie.id) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    it
                }
            }
        }

        if (fromDetails) {
            _movie.value = _movie.value?.let {
                it.copy(isFavorite = !it.isFavorite)
            }
        }

        sharedPreferencesHelper.toggleFavoriteMovie(movie.id)
    }

    fun searchMovies(query: String) {
        _searchQuery.value = query

        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            _responseState.value = ResponseState.Loading
            try {
                val response = apiService.searchMovies(AppConfig.API_KEY, query)

                val favoriteIds = sharedPreferencesHelper.getFavoriteMovies()

                _searchResults.value = response.results.map { movie ->
                    movie.copy(isFavorite = favoriteIds.contains(movie.id))
                }

                _responseState.value = ResponseState.Success

            } catch (e: Exception) {
                _responseState.value = ResponseState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setSearchState(state: Boolean) {
        _isSearch.value = state
    }
}