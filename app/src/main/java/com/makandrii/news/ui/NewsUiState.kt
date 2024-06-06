package com.makandrii.news.ui

import com.makandrii.news.data.model.News

data class NewsUiState(
    val tabIndex: Int = 0,
    val selectedNews: News? = null,
    val categories: List<Category> = listOf(
        Category(name = "home", uiDisplayName = "News"),
        Category(name = "business", uiDisplayName = "Бізнес"),
        Category(name = "crime", uiDisplayName = "Злочинність"),
        Category(name = "politics", uiDisplayName = "Політика"),
        Category(name = "science", uiDisplayName = "Наука"),
        Category(name = "sports", uiDisplayName = "Спорт"),
        Category(name = "technology", uiDisplayName = "Технології"),
        Category(name = "other", uiDisplayName = "Інше")
    ),
    val searchQuery: String = "",
    val searchedNews: List<News> = emptyList()
)

data class Category(
    val name: String,
    val uiDisplayName: String,
    var state: InternetConnectionState = InternetConnectionState.Loading
)

sealed interface InternetConnectionState {
    data class Success(
        val news: List<News>
    ) : InternetConnectionState

    data object Error : InternetConnectionState
    data object Loading : InternetConnectionState
}