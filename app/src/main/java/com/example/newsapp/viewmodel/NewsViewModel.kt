package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.News
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * ViewModel for News List
 */
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    var newsLiveData: LiveData<News>
        get() = repository.newsList
        set(value) {
            newsLiveData = value
        }

    private var job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    var category: String = ""

    init {
        getNewsFromAPi()
    }

    fun getNewsFromAPi() = scope.launch(Dispatchers.IO) {
        repository.getNewsByCategory(category)
    }
}