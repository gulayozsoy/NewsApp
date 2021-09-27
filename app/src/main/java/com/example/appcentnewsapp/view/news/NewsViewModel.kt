package com.example.appcentnewsapp.view.news

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.example.appcentnewsapp.model.News
import com.example.appcentnewsapp.repository.LocalRepository
import com.example.appcentnewsapp.repository.NewsRepository
import com.example.appcentnewsapp.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewsViewModel(private val repository: NewsRepository): ViewModel() {

    val showProgress = ObservableBoolean()
    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>> = _newsList

    private val _navigateToDetails = MutableLiveData<Event<News>>()
    val navigateToDetails: LiveData<Event<News>>
        get() = _navigateToDetails

    fun getQuery(query: String, page: Int) {
        showProgress.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = repository.getNews(query, page)
                if (list.isNotEmpty())
                    _newsList.postValue(list)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        showProgress.set(false)
    }

    fun itemClicked(newsItem: News) {
        _navigateToDetails.value = Event(newsItem)
    }
}
