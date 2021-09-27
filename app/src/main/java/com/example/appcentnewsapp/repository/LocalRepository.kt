package com.example.appcentnewsapp.repository

import androidx.annotation.WorkerThread
import com.example.appcentnewsapp.model.News
import com.example.appcentnewsapp.network.local.NewsDao
import kotlinx.coroutines.flow.Flow

class LocalRepository(private val newsDao: NewsDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allLocalNews: Flow<List<News>> = newsDao.getAllNews()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(news: News) {
        newsDao.insertNews(news)
    }

    @WorkerThread
    suspend fun deleteNews(news: News) {
        newsDao.deleteNews(news)
    }

    @WorkerThread
    suspend fun getSingleNews(title: String): News {
        return newsDao.getSingleNews(title)
    }
}