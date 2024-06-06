package com.makandrii.news.data

import android.content.Context
import com.makandrii.news.data.database.BookmarksDatabase
import com.makandrii.news.data.repository.BookmarksRepository
import com.makandrii.news.data.repository.NetworkNewsRepository
import com.makandrii.news.data.repository.NewsRepository
import com.makandrii.news.data.service.NewsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val newsRepository: NewsRepository
    val bookmarksRepository: BookmarksRepository
}

class DefaultAppContainer(
    private val context: Context,
    private val apiKey: String
) : AppContainer {

    private val baseUrl = "https://newsdata.io/api/1/"

    private val database by lazy {
        BookmarksDatabase.getDatabase(context)
    }

    override val bookmarksRepository by lazy {
        BookmarksRepository(database.bookmarksDao())
    }

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