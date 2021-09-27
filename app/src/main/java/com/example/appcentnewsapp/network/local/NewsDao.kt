package com.example.appcentnewsapp.network.local


import androidx.room.*
import com.example.appcentnewsapp.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<News>>

    @Query("SELECT * FROM news WHERE title = :title")
    fun getSingleNews(title: String): News

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: News)

    @Delete
    suspend fun deleteNews(news: News)

    @Query("DELETE FROM news")
    suspend fun deleteAll()

}