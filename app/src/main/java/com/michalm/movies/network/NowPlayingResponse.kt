package com.michalm.movies.network

import com.google.gson.annotations.SerializedName

data class NowPlayingResponse(
    @SerializedName("dates") val dates: Dates?,
    @SerializedName("page") val page: Int = 0,
    @SerializedName("results") val results: List<MovieModel>,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0
)