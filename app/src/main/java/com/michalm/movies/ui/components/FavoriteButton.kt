package com.michalm.movies.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.michalm.movies.R

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            painter =
            if (isFavorite)
                painterResource(id = R.drawable.ic_star)
            else
                painterResource(id = R.drawable.ic_star_outlined),
            tint = if (isFavorite)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface,
            contentDescription = "Favorite"
        )
    }
}