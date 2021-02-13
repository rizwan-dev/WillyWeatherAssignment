package com.willyweather.assignment.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.willyweather.assignment.R
import com.willyweather.assignment.databinding.RowNewsArticleBinding
import com.willyweather.assignment.repository.model.news.News

class NewsAdapter :  ListAdapter<News, NewsAdapter.NewsHolder>(NewsDiffCallback()) {

    var onNewsClicked: ((News) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsHolder(
            RowNewsArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(newsHolder: NewsHolder, position: Int) =
        newsHolder.bind(getItem(position))


    inner class NewsHolder(private val binding: RowNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsArticle: News) = with(itemView) {
            binding.apply {
                tvNewsItemTitle.text = newsArticle.title
                tvNewsAuthor.text = newsArticle.author
                tvListItemDateTime.text = newsArticle.publishedAt
                Glide.with(context)
                    .load(newsArticle.urlToImage)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_banner_image)
                            .error(R.drawable.no_image_available)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    .into(ivNewsImage)
            }

            itemView.setOnClickListener {
                onNewsClicked?.invoke(newsArticle)
            }

        }
    }
}

class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}