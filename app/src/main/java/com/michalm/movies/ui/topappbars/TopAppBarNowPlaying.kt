package com.michalm.movies.ui.topappbars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.michalm.movies.R
import com.michalm.movies.ui.MovieViewModel
import com.michalm.movies.ui.components.SearchField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNowPlaying(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel,
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isSearch by viewModel.isSearch.collectAsState()

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
                        query = searchQuery,
                        onValueChange = {
                            viewModel.searchMovies(it)
                        }
                    )
                } else {
                    Text(stringResource(R.string.now_playing))
                }
            },

            actions = {
                IconButton(
                    onClick = {
                        viewModel.setSearchState(!isSearch)
                        viewModel.searchMovies("")
                    }
                ) {
                    Icon(
                        imageVector = if (isSearch) Icons.Default.Close else Icons.Outlined.Search,
                        contentDescription = "Search Icon",
                    )
                }
            },
        )
    }
}