package com.michalm.movies.ui.components.topappbars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.michalm.movies.R
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.utils.NavDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNowPlaying(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MovieViewModel,
) {

    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()
    var isSearch by remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),

            title = {
                if (isSearch) {
                    SearchField(
                        modifier = Modifier.fillMaxWidth(),
                        query = query,
                        viewModel = viewModel,
                        onValueChange = { query = it }
                    )
                } else {
                    Text(stringResource(R.string.now_playing))
                }
            },

            actions = {
                IconButton(
                    onClick = {
                        isSearch = !isSearch
                    }
                ) {
                    Icon(
                        imageVector = if (isSearch) Icons.Default.Close else Icons.Outlined.Search,
                        contentDescription = "Search Icon",
                    )
                }
            },
        )

        if (isSearch && query.isNotEmpty()) {
            if (searchResults.isNotEmpty()) {
                SearchResultList(
                    searchResults, onMovieClick = { movie ->
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
}