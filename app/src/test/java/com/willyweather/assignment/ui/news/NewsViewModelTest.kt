package com.willyweather.assignment.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.willyweather.assignment.repository.api.network.Resource
import com.willyweather.assignment.repository.api.network.Status.*
import com.willyweather.assignment.repository.model.news.News
import com.willyweather.assignment.repository.repo.news.NewsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: NewsRepository

    @Mock
    private lateinit var newsDataObserver: Observer<Resource<List<News>?>>

    lateinit var newsViewModel: NewsViewModel

    @Before
    fun setUp() {
        newsViewModel = NewsViewModel(repository)
    }

    @Test
    fun getNewsArticles_shouldReturnSuccess() {

        val data = MutableLiveData<Resource<List<News>?>>().apply {
            value = Resource(SUCCESS, emptyList())
        }

        `when`(repository.getNewsArticles("au")).thenReturn(data)

        newsViewModel = NewsViewModel(repository)

        val result = newsViewModel.getNewsArticles("au")

        Assert.assertEquals(true, result.value!! == Resource(SUCCESS, emptyList<News>()))

    }


    @Test
    fun getNewsArticles_shouldReturnFailure() {

        val data = MutableLiveData<Resource<List<News>?>>().apply {
            value = Resource(status = ERROR, errorMessage = "Something went wrong")
        }

        `when`(repository.getNewsArticles("au")).thenReturn(data)

        newsViewModel = NewsViewModel(repository)

        val result = newsViewModel.getNewsArticles("au")

        Assert.assertEquals(true, result.value!!.status == ERROR)

    }

}