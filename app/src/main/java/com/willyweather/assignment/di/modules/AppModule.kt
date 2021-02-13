package com.willyweather.assignment.di.modules

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.kotlin.mvvm.di.modules.ViewModelModule
import com.willyweather.assignment.BuildConfig
import com.willyweather.assignment.app.App
import com.willyweather.assignment.repository.api.ApiServices
import com.willyweather.assignment.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import com.willyweather.assignment.repository.db.AppDatabase
import com.willyweather.assignment.repository.db.news.NewsDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [PreferencesModule::class, ActivityModule::class, ViewModelModule::class])
class AppModule {

    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
    }


    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideNewsService(): ApiServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .build()
            .create(ApiServices::class.java)
    }


    /**
     * Provides app AppDatabase
     */
    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "news-db")
            .fallbackToDestructiveMigration().build()


    /**
     * Provides NewsArticlesDao an object to access NewsArticles table from Database
     */
    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): NewsDao = db.newsArticlesDao()




    /**
     * Application application level context.
     */
    @Singleton
    @Provides
    fun provideContext(application: App): Context = application.applicationContext


    /**
     * Application resource provider, so that we can get the Drawable, Color, String etc at runtime
     */
    @Provides
    @Singleton
    fun providesResources(application: App): Resources = application.resources
}
