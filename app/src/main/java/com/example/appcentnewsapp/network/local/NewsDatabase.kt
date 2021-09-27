package com.example.appcentnewsapp.network.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appcentnewsapp.model.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        var INSTANCE: NewsDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NewsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                   NewsDatabase::class.java,
                    "news"
                )
                .fallbackToDestructiveMigration()
                .addCallback(NewsDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class NewsDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        val newsDao = database.newsDao()
                        populateDatabase(newsDao)
                    }
                }
            }
        }

        private suspend fun populateDatabase(newsDao: NewsDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            newsDao.deleteAll()

        }
    }
}