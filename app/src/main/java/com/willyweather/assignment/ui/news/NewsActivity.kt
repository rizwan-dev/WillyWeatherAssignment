package com.willyweather.assignment.ui.news

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.willyweather.assignment.databinding.ActivityNewsBinding
import com.willyweather.assignment.ui.BaseActivity
import com.willyweather.assignment.utils.extensions.toast
import com.willyweather.assignment.utils.extensions.viewBinding

class NewsActivity : BaseActivity() {

    private val binding by viewBinding(ActivityNewsBinding::inflate)

    companion object {
        const val COUNTRY_SHORT_KEY = "au"
    }

    private lateinit var adapter: NewsAdapter

    private val newsArticleViewModel: NewsViewModel by viewModels {
        viewModelFactory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = NewsAdapter()
        adapter.onNewsClicked = {
            toast(it.toString())
        }

        binding.newsList.adapter = adapter
        binding.newsList.layoutManager = LinearLayoutManager(this)

        getNewsOfCountry(COUNTRY_SHORT_KEY)
    }


    private fun getNewsOfCountry(countryKey: String) {
        newsArticleViewModel.getNewsArticles(countryKey).observe(this, Observer {
            when {
                it.status.isLoading() -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                it.status.isSuccessful() -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { news ->
                        adapter.submitList(news)
                    }
                }
                it.status.isError() -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.errorMessage != null)
                        toast(it.errorMessage.toString())
                }
            }
        })
    }
}
