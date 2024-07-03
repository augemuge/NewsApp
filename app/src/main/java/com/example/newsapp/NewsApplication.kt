package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.ApplicationComponent
import com.example.newsapp.di.DaggerApplicationComponent

class NewsApplication : Application() {

    lateinit var applicationComponent : ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}