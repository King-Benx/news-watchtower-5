package com.asiimwebenard.newswatchtower5.models

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asiimwebenard.newswatchtower5.R
import com.asiimwebenard.newswatchtower5.constants.API_KEY
import com.asiimwebenard.newswatchtower5.dao.StoredArticle
import com.asiimwebenard.newswatchtower5.dao.StoredArticleDao
import com.asiimwebenard.newswatchtower5.dao.StoredArticleDatabase
import com.asiimwebenard.newswatchtower5.services.NewsService
import com.asiimwebenard.newswatchtower5.services.NewsServiceBuilder
import com.asiimwebenard.newswatchtower5.shared.checkInternetConnection
import com.asiimwebenard.newswatchtower5.shared.showInternetAlertDialiog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModal(application: Application) : AndroidViewModel(application) {
    val storedArticles: LiveData<List<StoredArticle>>

    private val storedArticleDao: StoredArticleDao

    init {
        val articleDatabase = StoredArticleDatabase.getDatabase(application)
        storedArticleDao = articleDatabase!!.storedArticleDao()
        storedArticles = storedArticleDao.savedArticles
    }

    /**
     * Store articles.
     */
    fun storeArticle(storedArticle: StoredArticle) {
        InsertAsyncTask(
            storedArticleDao
        ).execute(storedArticle)
    }

    /**
     * Delete articles.
     */
    fun deleteArticle(storedArticle: StoredArticle) {
        DeleteAsyncTask(storedArticleDao)
            .execute(storedArticle)
    }

    /**
     * Load news based on a source or location.
     */
    fun loadNewsArticle(context: Context, criteria: Pair<String, String>): LiveData<NewsReport>? {
        if (checkInternetConnection(context)) {
            val newsService = NewsServiceBuilder.builderService(NewsService::class.java)
            val filters = HashMap<String, String>()
            val newsReportRequest: Call<NewsReport>
            filters["apiKey"] = API_KEY
            if (criteria.first == context.getString(R.string.location)) {
                filters["q"] = criteria.second
                newsReportRequest = newsService.getNewsUpdates(filters)
            } else {
                filters["sources"] = criteria.second
                newsReportRequest = newsService.getTopHeadlines(filters)
            }
            val data = MutableLiveData<NewsReport>()
            newsReportRequest.enqueue(object : Callback<NewsReport> {
                override fun onFailure(call: Call<NewsReport>, t: Throwable) {
                    Log.d("CRASH", t.message.toString())
                }

                override fun onResponse(
                    call: Call<NewsReport>,
                    response: Response<NewsReport>
                ) {
                    data.value = response.body()
                }

            })
            return data
        } else {
            showInternetAlertDialiog(context)
            return null
        }
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
