package com.sgupta.composite.api

import com.sgupta.composite.model.MovieAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieHubAPIService {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(@Query("language") language: String = "en-US"): Response<MovieAPIResponse>
}