package com.michalm.movies.ui.nowplaying.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.michalm.movies.R
import com.michalm.movies.network.MovieModel
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.utils.NavDestinations

@Composable
fun SearchSection(
    searchResults: List<MovieModel>,
    navController: NavController,
    viewModel: MovieViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (searchResults.isNotEmpty()) {

            SearchResultList(
                movieList = searchResults,
                onMovieClick = { movie ->
                    viewModel.selectMovie(movie)
                    navController.navigate(NavDestinations.MOVIE_DETAILS_ROUTE)
                }
            )
        } else {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.no_results_found)
            )
        }
    }
}