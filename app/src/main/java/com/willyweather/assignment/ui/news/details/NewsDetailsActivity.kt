package com.willyweather.assignment.ui.news.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.willyweather.assignment.R
import com.willyweather.assignment.databinding.ActivityNewDetailsBinding
import com.willyweather.assignment.repository.model.news.News
import com.willyweather.assignment.utils.extensions.viewBinding

class NewsDetailsActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityNewDetailsBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val news = intent.getParcelableExtra<News>(KEY_NEWS)
        news?.let {
            showData(it)
        }
        enableActionBarBackButton()

    }

    private fun enableActionBarBackButton() {
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showData(news: News) {

        binding.apply {
            tvAuthorName.text = news.author ?: ""
            tvTitle.text = news.title ?: ""
            tvDescription.text = news.description ?: ""
            tvDateTime.text = news.publishedAt ?: ""
            Glide.with(this@NewsDetailsActivity)
                .load(news.urlToImage)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_banner_image)
                        .error(R.drawable.no_image_available)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(ivNewsImage)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {

        private const val KEY_NEWS = "news"
        fun newInstance(context: Context, news: News): Intent {
            return Intent(context, NewsDetailsActivity::class.java).apply {
                putExtra(
                    KEY_NEWS,
                    news
                )
            }
        }
    }
}