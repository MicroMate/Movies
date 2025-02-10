package com.michalm.movies.ui.nowplaying

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.michalm.movies.network.MovieModel
import com.michalm.movies.ui.components.FavoriteButton
import com.michalm.movies.ui.theme.MoviesTheme
import com.michalm.movies.utils.AppConfig.IMAGE_URL
import com.michalm.movies.utils.PreviewMock

@Composable
fun MovieCard(
    movie: MovieModel,
    onClick: () -> Unit,
    onFavoriteClick: (MovieModel) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .clickable { onClick() }
    ) {
        movie.posterPath?.let { posterPath ->
            val imageUrl = "$IMAGE_URL$posterPath"
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = movie.title ?: "Title is missing",
                fontWeight = FontWeight.Bold
            )

            FavoriteButton(
                isFavorite = movie.isFavorite,
                onClick = { onFavoriteClick(movie) }
            )
        }
    }
}


@Preview(
    name = "Dark Mode Preview",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    //showBackground = true
)
@Composable
fun PreviewMovieItem() {

    MoviesTheme {
        MovieCard(
            movie = PreviewMock.movie,
            onClick = {},
            onFavoriteClick = {}
        )
    }
}