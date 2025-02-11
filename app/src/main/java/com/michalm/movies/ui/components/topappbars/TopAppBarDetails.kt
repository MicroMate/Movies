package com.michalm.movies.ui.components.topappbars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.michalm.movies.R
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.ui.components.FavoriteButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDetails(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MovieViewModel,
) {
    val movie by viewModel.movie.collectAsState()

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back"
                )
            }
        },
        actions = {

            movie?.let { movie ->
                FavoriteButton(
                    isFavorite = movie.isFavorite,
                    onClick = { viewModel.toggleFavorite(movie) }
                )
            }
        },
    )
}


