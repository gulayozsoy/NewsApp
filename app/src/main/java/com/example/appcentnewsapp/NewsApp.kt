package com.example.appcentnewsapp

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.example.appcentnewsapp.di.*
import com.example.appcentnewsapp.network.local.NewsDatabase
import com.example.appcentnewsapp.repository.LocalRepository
import com.facebook.stetho.Stetho
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // the database and the repository are only created once when they're needed
    val database by lazy { NewsDatabase.getDatabase(this, applicationScope) }
    val localRepository by lazy { LocalRepository(database.newsDao()) }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules( listOf(
                apiModule,
                viewModelModule,
                repositoryModule,
                networkModule
            ))
        }

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}