package com.makandrii.news.data

import com.makandrii.news.data.repository.NetworkNewsRepository
import com.makandrii.news.data.repository.NewsRepository
import com.makandrii.news.data.service.NewsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val newsRepository: NewsRepository
}

class DefaultAppContainer(
    private val apiKey: String
) : AppContainer {

    private val baseUrl = "https://newsdata.io/api/1/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }

    override val newsRepository: NewsRepository by lazy {
        NetworkNewsRepository(retrofitService, apiKey)
    }
}