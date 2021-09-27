package com.example.appcentnewsapp.repository

import com.example.appcentnewsapp.model.News

interface NewsRepository {
    suspend fun getNews(query: String, page: Int): List<News>
}