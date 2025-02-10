package com.michalm.movies.ui.nowplaying

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.michalm.movies.network.MovieModel
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.utils.NavDestinations
import com.michalm.movies.utils.ResponseState


@Composable
fun NowPlayingScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: MovieViewModel
) {
    val responseState by viewModel.responseState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    )
    {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        when (responseState) {
            is ResponseState.Success -> {
                MovieGrid(
                    movieList = (responseState as ResponseState.Success).data ?: emptyList(),
                    navController = navController,
                    viewModel = viewModel
                )
            }
            is ResponseState.Error ->
                Text((responseState as ResponseState.Error).message)
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
                onFavoriteClick = { viewModel.toggleFavorite(it) }
            )
        }
        item {
            LaunchedEffect(Unit) {
                viewModel.loadMovies()
            }
        }
    }
}