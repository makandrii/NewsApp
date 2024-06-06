package com.makandrii.news

import android.app.Application
import com.makandrii.news.data.AppContainer
import com.makandrii.news.data.DefaultAppContainer

class MainApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this, applicationContext.getString(R.string.API_KEY))
    }
}