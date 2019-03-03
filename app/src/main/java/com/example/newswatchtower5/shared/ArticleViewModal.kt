package com.example.newswatchtower5.shared

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.newswatchtower5.dao.StoredArticle
import com.example.newswatchtower5.dao.StoredArticleDao
import com.example.newswatchtower5.dao.StoredArticleDatabase

class ArticleViewModal(application: Application) : AndroidViewModel(application) {
    val storedArticles: LiveData<List<StoredArticle>>

    private val storedArticleDao: StoredArticleDao

    init {
        val articleDatabase = StoredArticleDatabase.getDatabase(application)
        storedArticleDao = articleDatabase!!.storedArticleDao()
        storedArticles = storedArticleDao.savedArticles
    }

    fun storeArticle(storedArticle: StoredArticle) {
        InsertAsyncTask(
            storedArticleDao
        ).execute(storedArticle)
    }

    fun deleteArticle(storedArticle: StoredArticle) {
        DeleteAsyncTask(storedArticleDao).execute(storedArticle)
    }

    companion object {
        private class InsertAsyncTask(private val storedArticleDao: StoredArticleDao) :
            AsyncTask<StoredArticle, Void, Void>() {
            override fun doInBackground(vararg articles: StoredArticle?): Void? {
                storedArticleDao.storeArticle(articles[0]!!)
                return null
            }

        }

        private class DeleteAsyncTask(private val storedArticleDao: StoredArticleDao) :
            AsyncTask<StoredArticle, Void, Void>() {
            override fun doInBackground(vararg articles: StoredArticle?): Void? {
                storedArticleDao.deleteArticle(articles[0]!!)
                return null
            }

        }
    }
}
