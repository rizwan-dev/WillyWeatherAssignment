package com.willyweather.assignment.repository.db.news

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.willyweather.assignment.repository.model.news.News


@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<News>): List<Long>

    @Query("SELECT * FROM news_table")
    fun getNewsArticles(): LiveData<List<News>>

    @Query("DELETE FROM news_table")
    abstract fun deleteAllArticles()
}