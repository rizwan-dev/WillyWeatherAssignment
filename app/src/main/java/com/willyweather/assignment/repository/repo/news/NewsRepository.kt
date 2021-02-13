package com.willyweather.assignment.repository.repo.news

import android.content.Context
import androidx.lifecycle.LiveData
import com.willyweather.assignment.repository.api.network.NetworkAndDBBoundResource
import com.willyweather.assignment.BuildConfig
import com.willyweather.assignment.app.AppExecutors
import com.willyweather.assignment.repository.api.ApiServices
import com.willyweather.assignment.repository.api.network.NetworkResource
import com.willyweather.assignment.repository.api.network.Resource
import com.willyweather.assignment.repository.db.news.NewsDao
import com.willyweather.assignment.repository.model.news.News
import com.willyweather.assignment.repository.model.news.NewsSource
import com.willyweather.assignment.utils.ConnectivityUtil
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val apiServices: ApiServices, private val context: Context,
    private val appExecutors: AppExecutors = AppExecutors()
) {


    fun getNewsArticles(countryShortKey: String): LiveData<Resource<List<News>?>> {
        val data = HashMap<String, String>()
        data["country"] = countryShortKey
        data["apiKey"] = BuildConfig.NEWS_API_KEY

        return object : NetworkAndDBBoundResource<List<News>, NewsSource>(appExecutors) {
            override fun saveCallResult(item: NewsSource) {
                if (item.articles.isNotEmpty()) {
                    newsDao.deleteAllArticles()
                    newsDao.insertArticles(item.articles)
                }
            }

            override fun shouldFetch(data: List<News>?) =
                (ConnectivityUtil.isConnected(context))

            override fun loadFromDb() = newsDao.getNewsArticles()

            override fun createCall() =
                apiServices.getNewsSource(data)

        }.asLiveData()
    }

    /**
     * Fetch the news articles from database if exist else fetch from web
     * and persist them in the database
     * LiveData<Resource<NewsSource>>
     */
    fun getNewsArticlesFromServerOnly(countryShortKey: String):
            LiveData<Resource<NewsSource>> {

        val data = HashMap<String, String>()
        data["country"] = countryShortKey
        data["apiKey"] = BuildConfig.NEWS_API_KEY

        return object : NetworkResource<NewsSource>() {
            override fun createCall(): LiveData<Resource<NewsSource>> {
                return apiServices.getNewsSource(data)
            }

        }.asLiveData()
    }

}