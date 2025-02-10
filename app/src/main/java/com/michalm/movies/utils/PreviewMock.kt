package com.michalm.movies.utils

import com.michalm.movies.network.MovieModel

object PreviewMock {

    val movie = MovieModel(
        title = "The Batman",
        overview = "When a sadistic killer begins murdering key political figures in Gotham, Batman is forced to investigate the city's hidden corruption...",
        posterPath = "/jX2LQW0J7f02gncLPWRNxoaV6Vd.jpg",
        releaseDate = "2022-03-04",
        voteAverage = 7.8,
        voteCount = 12345,
        adult = false,
        backdropPath = "/h6hdU75ShpOBn5Wzjm63X6YyySo.jpg",
        genreIds = listOf(28, 12, 80),
        id = 123456,
        originalLanguage = "en",
        originalTitle = "The Batman",
        popularity = 80.0,
        video = false
    )
}