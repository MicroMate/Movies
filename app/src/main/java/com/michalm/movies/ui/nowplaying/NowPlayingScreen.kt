package com.michalm.movies.ui.nowplaying

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.ui.nowplaying.movielist.MovieGrid
import com.michalm.movies.ui.nowplaying.search.SearchSection


@Composable
fun NowPlayingScreen(
    navController: NavController,
    viewModel: MovieViewModel
) {
    val movieList by viewModel.movieListResult.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearch by viewModel.isSearch.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
    ) {
        MovieGrid(
            movieList = movieList,
            navController = navController,
            viewModel = viewModel
        )

        if (isSearch && searchQuery.isNotBlank()) {
            SearchSection(
                searchResults = searchResults,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}