package com.michalm.movies.ui.nowplaying.movielist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.michalm.movies.network.MovieModel
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.utils.NavDestinations

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