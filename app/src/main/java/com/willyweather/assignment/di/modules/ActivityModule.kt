package com.willyweather.assignment.di.modules

import com.willyweather.assignment.ui.news.NewsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * All your Activities participating in DI would be listed here.
 */
@Module
abstract class ActivityModule {

    /**
     * Marking Activities to be available to contributes for Android Injector
     */
    @ContributesAndroidInjector
    abstract fun contributeNewsArticlesActivity(): NewsActivity


}
