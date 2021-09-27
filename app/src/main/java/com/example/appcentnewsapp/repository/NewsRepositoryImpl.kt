package com.example.appcentnewsapp.repository


import com.example.appcentnewsapp.Utils.handleApiError
import com.example.appcentnewsapp.Utils.handleSuccess
import com.example.appcentnewsapp.model.News
import com.example.appcentnewsapp.network.remote.API_KEY
import com.example.appcentnewsapp.network.remote.NewsAPIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(private val apiService: NewsAPIInterface): NewsRepository {

    override suspend fun getNews(query: String, page: Int): List<News> {
        var result = listOf<News>()
        val response = apiService.getNews(query, page, API_KEY)
        if (response.isSuccessful) {
            response.body()?.let {
                withContext(Dispatchers.IO) { result = it.result!! }
            }
            handleSuccess(response)
        } else {
            handleApiError(response)
        }
        return result
    }

}