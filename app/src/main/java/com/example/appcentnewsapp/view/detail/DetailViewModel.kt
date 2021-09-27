package com.example.appcentnewsapp.view.detail

import androidx.lifecycle.*
import com.example.appcentnewsapp.model.News
import com.example.appcentnewsapp.repository.LocalRepository
import com.example.appcentnewsapp.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: LocalRepository): ViewModel() {

    private val _shareEvent = MutableLiveData<Event<Unit>>()
    val shareEvent: LiveData<Event<Unit>>
        get() = _shareEvent

    private val _openWebEvent = MutableLiveData<Event<Unit>>()
    val openWebEvent: LiveData<Event<Unit>>
        get() = _openWebEvent

    private val _favoriteIconVisibility = MutableLiveData(false)
    val favoriteIconVisibility : LiveData<Boolean>
        get() = _favoriteIconVisibility


    fun sourceClick() {
        _openWebEvent.value = Event(Unit)
    }

    fun shareClick() {
        _shareEvent.value = Event(Unit)
    }

    fun addToFavorites(news: News) {
        if(_favoriteIconVisibility.value == false) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.insert(news)
                    _favoriteIconVisibility.postValue(true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else
            deleteFavorite(news)
    }

    fun checkFavorites(newsItem: News) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tempData = newsItem.title?.let { repository.getSingleNews(it) }

                if (tempData!= null) {
                    _favoriteIconVisibility.postValue(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteFavorite(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteNews(news)
                _favoriteIconVisibility.postValue(false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

class DetailViewModelFactory(private val repository: LocalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}