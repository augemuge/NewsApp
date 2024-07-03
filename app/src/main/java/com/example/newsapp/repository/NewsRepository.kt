package com.example.newsapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.model.News
import com.example.newsapp.retrofit.NewsApi
import com.example.newsapp.utils.Constants
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {

    private val news = MutableLiveData<News>()
    val newsList: LiveData<News>
        get() = news

    suspend fun getNewsByCategory(category: String){
        val result = newsApi.getAllNews(category, Constants.API_KEY)
        if(result.isSuccessful && result.body() != null){
            news.postValue(result.body())
        }
    }
}