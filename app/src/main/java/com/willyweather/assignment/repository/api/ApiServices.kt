package com.willyweather.assignment.repository.api

import androidx.lifecycle.LiveData
import com.willyweather.assignment.repository.api.network.Resource
import com.willyweather.assignment.repository.model.news.NewsSource
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiServices {

    @GET("top-headlines")
    fun getNewsSource(@QueryMap options: Map<String, String>): LiveData<Resource<NewsSource>>

}
