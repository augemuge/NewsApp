package com.example.newsapp.retrofit

import com.example.newsapp.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET ("top-headlines?country=us")
    suspend fun getAllNews(@Query("category") category: String?, @Query("apiKey") apiKey: String?) : Response<News>

}