package com.willyweather.assignment.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.willyweather.assignment.repository.api.network.Resource
import com.willyweather.assignment.repository.model.news.News
import com.willyweather.assignment.repository.repo.news.NewsRepository
import javax.inject.Inject


class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private fun newsArticles(countryKey: String): LiveData<Resource<List<News>?>> =
        newsRepository.getNewsArticles(countryKey)


    fun getNewsArticles(countryKey: String) = newsArticles(countryKey)

}