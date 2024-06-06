package com.makandrii.news.data.repository

import com.makandrii.news.data.model.News
import com.makandrii.news.data.service.NewsApiService

interface NewsRepository {
    suspend fun getNews(
        country: String,
        language: String,
        options: Map<String, String> = emptyMap()
    ): List<News>
}

class NetworkNewsRepository(
    private val newsApiService: NewsApiService,
    private val apiKey: String
) : NewsRepository {
    override suspend fun getNews(
        country: String,
        language: String,
        options: Map<String, String>
    ): List<News> = newsApiService.getNews(apiKey, country, language, options).results
}