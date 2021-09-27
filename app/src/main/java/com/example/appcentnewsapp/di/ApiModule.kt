package com.example.appcentnewsapp.di

import com.example.appcentnewsapp.network.remote.NewsAPIInterface
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideNewsApi(retrofit: Retrofit): NewsAPIInterface {
        return retrofit.create( NewsAPIInterface::class.java)
    }
    single { provideNewsApi(get()) }

}