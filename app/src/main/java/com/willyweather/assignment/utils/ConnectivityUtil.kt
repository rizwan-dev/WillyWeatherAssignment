package com.willyweather.assignment.utils

import android.content.Context
import android.net.ConnectivityManager



object ConnectivityUtil {

    @Suppress("DEPRECATION")
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}