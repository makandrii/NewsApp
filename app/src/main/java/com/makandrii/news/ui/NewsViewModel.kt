package com.makandrii.news.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.makandrii.news.MainApplication
import com.makandrii.news.data.model.News
import com.makandrii.news.data.repository.BookmarksRepository
import com.makandrii.news.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        updateBookmarks()
    }

    private fun updateBookmarks() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    bookmarks = bookmarksRepository.getBookmarkedNews()
                )
            }
        }
    }

    fun setTabIndex(index: Int) {
        _uiState.update {
            it.copy(
                tabIndex = index
            )
        }
    }

    fun getNews(category: String?) {
        viewModelScope.launch {
            try {
                val listNews = newsRepository.getNews(
                    country = "ua",
                    language = "uk",
                    options = category?.let {
                        mapOf("category" to category)
                    } ?: emptyMap()
                )
                updateCategoryState(category, InternetConnectionState.Success(listNews))
            } catch (e: IOException) {
                updateCategoryState(category, InternetConnectionState.Error)
            }
        }
    }

    /*private suspend fun getNewsFromApi(options: Map<String, String>): List<News> {
        return newsRepository.getNews(
            country = "ua",
            language = "uk",
            options = mapOf("q" to searchQuery)
        ).map {
            it.isBookmarked = bookmarksRepository.isExist(it.id)
            it
        }
    }*/

    private fun updateCategoryState(category: String?, newState: InternetConnectionState) {
        _uiState.update { currentState ->
            val updatedCategories = currentState.categories.map {
                if ((category ?: "home") == it.name) {
                    it.copy(state = newState)
                } else {
                    it
                }
            }
            currentState.copy(categories = updatedCategories)
        }
    }

    fun searchNews(searchQuery: String) {
        viewModelScope.launch {
            try {
                val listNews = newsRepository.getNews(
                    country = "ua",
                    language = "uk",
                    options = mapOf("q" to searchQuery)
                )
                _uiState.update {
                    it.copy(
                        searchedNews = listNews
                    )
                }
            } catch (e: IOException) {
                _uiState.update {
                    it.copy(
                        searchedNews = emptyList()
                    )
                }
            }
        }
    }

    fun selectNews(news: News?) {
        _uiState.update {
            it.copy(
                selectedNews = news
            )
        }
    }

    fun addBookmark(news: News) {
        news.isBookmarked = true
        viewModelScope.launch {
            bookmarksRepository.addBookmark(news)
            updateBookmarks()
        }
    }

    fun removeBookmark(news: News) {
        news.isBookmarked = false
        viewModelScope.launch {
            bookmarksRepository.removeBookmark(news)
            updateBookmarks()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                val newsRepository = application.container.newsRepository
                val bookmarksRepository = application.container.bookmarksRepository

                NewsViewModel(newsRepository, bookmarksRepository)
            }
        }
    }
}