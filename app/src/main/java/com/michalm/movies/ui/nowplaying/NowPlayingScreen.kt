package com.michalm.movies.ui.nowplaying

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.michalm.movies.network.MovieModel
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.ui.components.Banner
import com.michalm.movies.utils.NavDestinations
import com.michalm.movies.utils.ResponseState


@Composable
fun NowPlayingScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: MovieViewModel
) {
    val responseState by viewModel.responseState.collectAsState()
    val movieList by viewModel.movieListResult.collectAsState()

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
    }
}


@Composable
fun MovieGrid(
    movieList: List<MovieModel>,
    navController: NavController,
    viewModel: MovieViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movieList) { movie ->
            MovieCard(
                movie = movie,
                onClick = {
                    viewModel.selectMovie(movie)
                    navController.navigate(NavDestinations.MOVIE_DETAILS_ROUTE)
                },
                onFavoriteClick = {
                    viewModel.toggleFavorite(movie = it, fromDetails = false)
                }
            )
        }
        item {
            LaunchedEffect(Unit) {
                viewModel.loadMovies()
            }
        }
    }
}