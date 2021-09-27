package com.example.appcentnewsapp.di

import android.app.Application
import androidx.room.Room
import com.example.appcentnewsapp.network.local.NewsDao
import com.example.appcentnewsapp.network.local.NewsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/*val databaseModule = module {

    fun provideDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(application, NewsDatabase::class.java, "news")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: NewsDatabase): NewsDao {
        return  database.newsDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }

}*/