package com.michalm.movies.utils

import com.michalm.movies.network.Dates
import com.michalm.movies.network.MovieModel
import com.michalm.movies.network.MovieSearchResponse
import com.michalm.movies.network.NowPlayingResponse

object MockData {

    private val mockDates = Dates(maximum = "2024-12-31", minimum = "2024-01-01")

    val mockMovie = MovieModel(
        adult = false,
        backdropPath = null,
        genreIds = listOf(28, 12),
        id = 1,
        originalLanguage = "en",
        originalTitle = "Test Original Movie Title",
        overview = "A test movie overview",
        popularity = 5.0,
        posterPath = "/test_poster.jpg",
        releaseDate = "2024-01-01",
        title = "Test Movie Title",
        video = false,
        voteAverage = 8.0,
        voteCount = 100,
        isFavorite = false
    )

    val mockNowPlayingResponse = NowPlayingResponse(
        dates = mockDates,
        page = 1,
        results = listOf(mockMovie),
        totalPages = 1,
        totalResults = 1
    )

    val mockMovieSearchResponse = MovieSearchResponse(
        results = listOf(mockMovie)
    )
}