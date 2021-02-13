package com.willyweather.assignment.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.willyweather.assignment.repository.db.news.NewsDao
import com.willyweather.assignment.repository.model.news.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsArticlesDao(): NewsDao
}