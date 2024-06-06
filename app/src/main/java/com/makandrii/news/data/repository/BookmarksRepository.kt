package com.makandrii.news.data.repository

import com.makandrii.news.data.database.BookmarksDao
import com.makandrii.news.data.model.News

class BookmarksRepository(private val bookmarksDao: BookmarksDao) {
    suspend fun addBookmark(news: News) = bookmarksDao.insertBookmark(news)

    suspend fun removeBookmark(news: News) = bookmarksDao.deleteBookmark(news)

    suspend fun getBookmarkedNews() = bookmarksDao.getAllBookmarks()
}