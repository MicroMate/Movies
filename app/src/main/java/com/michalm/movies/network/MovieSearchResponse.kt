package com.michalm.movies.network

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("results") val results: List<MovieModel>
)