package com.makandrii.news.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.makandrii.news.MainApplication
import com.makandrii.news.data.model.News
import com.makandrii.news.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

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
                            mapOf("category" to it)
                        } ?: emptyMap()
                    )
                updateCategoryState(category, InternetConnectionState.Success(listNews))
            } catch (e: IOException) {
                updateCategoryState(category, InternetConnectionState.Error)
            }
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

    fun selectNews(news: News?) {
        _uiState.update {
            it.copy(
                selectedNews = news
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                val repository = application.container.newsRepository
                NewsViewModel(repository)
            }
        }
    }
}