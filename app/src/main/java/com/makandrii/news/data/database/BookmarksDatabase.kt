package com.makandrii.news.data.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.makandrii.news.data.model.News

@Dao
interface BookmarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(news: News)

    @Query("SELECT * FROM bookmarks")
    suspend fun getAllBookmarks(): List<News>

    @Delete
    suspend fun deleteBookmark(news: News)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE id = :newsId)")
    suspend fun isExist(newsId: String): Boolean
}

@Database(entities = [News::class], version = 1)
abstract class BookmarksDatabase : RoomDatabase() {
    abstract fun bookmarksDao(): BookmarksDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarksDatabase? = null

        fun getDatabase(context: Context): BookmarksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarksDatabase::class.java,
                    "bookmarks"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}