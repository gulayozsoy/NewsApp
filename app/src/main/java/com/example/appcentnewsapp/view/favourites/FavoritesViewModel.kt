package com.example.appcentnewsapp.view.favourites

import androidx.lifecycle.*
import com.example.appcentnewsapp.model.News
import com.example.appcentnewsapp.repository.LocalRepository
import com.example.appcentnewsapp.utils.Event


class FavoritesViewModel(private val repository: LocalRepository): ViewModel() {


    private val _navigateToDetails = MutableLiveData<Event<News>>()
    val navigateToDetails: LiveData<Event<News>>
        get() = _navigateToDetails

    var allLocalNews: LiveData<List<News>>? = null


    fun itemClicked(newsItem: News) {
        _navigateToDetails.value = Event(newsItem)
    }

    fun getLocalData() {
        allLocalNews = repository.allLocalNews.asLiveData()
    }

}

class FavoritesViewModelFactory(private val repository: LocalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
