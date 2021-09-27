package com.example.appcentnewsapp.di

import com.example.appcentnewsapp.network.remote.NewsAPIInterface
import com.example.appcentnewsapp.repository.NewsRepository
import com.example.appcentnewsapp.repository.NewsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideNewsRepository(api: NewsAPIInterface ): NewsRepository {
        return NewsRepositoryImpl(api)
    }
    single { provideNewsRepository(get()) }

    /*fun provideDetailsRepository(dao: NewsDao): DetailRepository {
        return DetailDetailRepositoryImpl(dao)
    }

    single { provideDetailsRepository(get()) }*/
}

