package com.makandrii.news.data.service

import com.makandrii.news.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NewsApiService {
    @GET("latest")
    suspend fun getNews(
        @Query("apikey") apiKey: String,
        @Query("country") country: String,
        @Query("language") language: String,
        @QueryMap options: Map<String, String> = mapOf()
    ): ApiResponse
}