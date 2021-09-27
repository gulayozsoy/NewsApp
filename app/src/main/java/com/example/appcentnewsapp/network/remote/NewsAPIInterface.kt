package com.example.appcentnewsapp.network.remote

import com.example.appcentnewsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "670341cebb8e47029a5ce6fe771bf2d6"

interface NewsAPIInterface {
    @GET("everything")
    suspend fun getNews(@Query("q") query:String,
                @Query("page") page:Int,
                @Query("apiKey") apiKey:String): Response<NewsResponse>

}