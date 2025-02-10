package com.michalm.movies.ui.details

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.michalm.movies.R
import com.michalm.movies.network.MovieModel
import com.michalm.movies.ui.theme.MoviesTheme
import com.michalm.movies.utils.AppConfig.IMAGE_URL
import com.michalm.movies.utils.PreviewMock

@Composable
fun DetailsContent(
    movie: MovieModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        movie.backdropPath?.let { backdropPath ->
            val imageUrl = "$IMAGE_URL$backdropPath"
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.title ?: stringResource(R.string.title_is_unavailable),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.overview ?: stringResource(R.string.overview_is_unavailable)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.date_release) + " ${movie.releaseDate}",
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.rating) + " ${movie.voteAverage} (${movie.voteCount} votes)",
        )
    }
}


@Preview(
    name = "Dark Mode Preview",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = false
)
@Composable
fun PreviewDetailsContent() {

    MoviesTheme {
        DetailsContent(movie = PreviewMock.movie)
    }
}