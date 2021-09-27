package com.example.appcentnewsapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    @SerializedName("articles") val result: List<News>?
): Parcelable

@Entity(tableName = "news",
    indices = [Index(value = ["title", "author", "date"], unique = true)])
@Parcelize
data class News(
    @PrimaryKey(autoGenerate = true)
    var uid: Int,
    @SerializedName( "title")
    val title: String?,
    @SerializedName("author")
    val author: String?,
    @SerializedName( "urlToImage")
    val urlToImage: String?,
    @SerializedName("description" )
    val description: String?,
    @SerializedName("publishedAt")
    val date: String?,
    @SerializedName( "content")
    val content: String?,
    @SerializedName( "url")
    val newsUrl: String?
): Parcelable