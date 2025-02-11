package com.michalm.movies.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michalm.movies.network.MovieModel


@Composable
fun SearchResultList(movieList: List<MovieModel>, onMovieClick: (MovieModel) -> Unit) {
    LazyColumn {
        items(movieList) { movie ->
            MovieItem(movie, onMovieClick)
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieModel,
    onMovieClick: (MovieModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onMovieClick(movie) }
    ) {

        Column(modifier = Modifier.padding(start = 8.dp)) {
            movie.title?.let { Text(text = it) }
        }
    }
}

