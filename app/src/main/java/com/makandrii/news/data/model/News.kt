package com.makandrii.news.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class News (
    @SerializedName("article_id") val id: String,
    val title: String,
    val content: String,
    val pubDate: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("source_url") val sourceUrl: String
)