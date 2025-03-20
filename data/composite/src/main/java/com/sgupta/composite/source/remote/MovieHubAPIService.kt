package com.sgupta.composite.source.remote

import com.sgupta.composite.model.MovieAPIResponse
import com.sgupta.composite.model.MovieDetailAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieHubAPIService {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(@Query("language") language: String = "en-US"): Response<MovieAPIResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<MovieAPIResponse>

    @GET("search/collection")
    suspend fun getMovieQuery(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("query") query: String,
    ): Response<MovieAPIResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "en-US"
    ): Response<MovieDetailAPIResponse>

}