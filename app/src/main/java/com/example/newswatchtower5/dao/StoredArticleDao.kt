package com.example.newswatchtower5.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StoredArticleDao {

    @Insert
    fun storeArticle(article: StoredArticle)

    @get:Query("Select * FROM stored_articles")
    val savedArticles: LiveData<List<StoredArticle>>

    @Delete
    fun deleteArticle(article: StoredArticle)
}
