package com.michalm.movies.ui.details

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.michalm.movies.ui.MovieViewModel

@Composable
fun DetailsScreen(
    viewModel: MovieViewModel,
) {
    val scrollState = rememberScrollState()
    val movie by viewModel.movie.collectAsState()

    Surface(
        modifier = Modifier
            .verticalScroll(scrollState),
        color = MaterialTheme.colorScheme.background
    )
    {
        movie?.let { movie ->
            DetailsContent(movie)
        }
    }
}

