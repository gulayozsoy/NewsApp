package com.example.appcentnewsapp.di

import com.example.appcentnewsapp.view.detail.DetailViewModel
import com.example.appcentnewsapp.view.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        NewsViewModel(get())
    }
    /*viewModel {
        DetailViewModel(get())
    }*/
}