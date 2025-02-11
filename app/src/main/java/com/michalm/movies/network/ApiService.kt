package com.michalm.movies.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun fetchNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): NowPlayingResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): MovieSearchResponse
}