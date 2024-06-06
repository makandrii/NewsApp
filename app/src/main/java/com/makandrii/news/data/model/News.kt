package com.makandrii.news.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "bookmarks")
data class News(
    @SerializedName("article_id") @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val pubDate: String,
    @SerializedName("image_url") @ColumnInfo(name = "image_url") val imageUrl: String?,
    @SerializedName("source_url") @ColumnInfo(name = "source_url") val sourceUrl: String,
    @Expose var isBookmarked: Boolean = false
)