package com.michalm.movies.ui.nowplaying

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.ui.components.Banner
import com.michalm.movies.ui.nowplaying.movielist.MovieGrid
import com.michalm.movies.ui.nowplaying.search.SearchSection
import com.michalm.movies.utils.ResponseState


@Composable
fun NowPlayingScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: MovieViewModel
) {
    val responseState by viewModel.responseState.collectAsState()
    val movieList by viewModel.movieListResult.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearch by viewModel.isSearch.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            when (responseState) {
                is ResponseState.Loading -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                is ResponseState.Error ->
                    Banner(text = (responseState as ResponseState.Error).message)

                else -> {}
            }
            MovieGrid(
                movieList = movieList,
                navController = navController,
                viewModel = viewModel
            )
        }

        if (isSearch && searchQuery.isNotBlank()) {
            SearchSection(
                searchResults = searchResults,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}