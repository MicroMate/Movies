package com.michalm.movies.ui.components.topappbars

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import com.michalm.movies.R
import com.michalm.movies.ui.MovieViewModel

@Composable
fun SearchField(
    modifier: Modifier,
    query: String,
    viewModel: MovieViewModel,
    onValueChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = query,
        onValueChange = {
            onValueChange(it)
            viewModel.searchMovies(it)
        },
        placeholder = {
            Text(
                stringResource(R.string.search_field_placeholder)
            )},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
        )
    )
}