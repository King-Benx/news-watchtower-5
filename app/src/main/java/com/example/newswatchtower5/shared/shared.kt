package com.example.newswatchtower5.shared

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newswatchtower5.adapters.NewsAdapter
import com.example.newswatchtower5.constants.API_KEY
import com.example.newswatchtower5.models.NewsReport
import com.example.newswatchtower5.services.NewsService
import com.example.newswatchtower5.services.NewsServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun loadData(
    context: Context,
    recyclerView: RecyclerView,
    location: String,
    swipeRefreshLayout: SwipeRefreshLayout? = null
) {
    val newsService = NewsServiceBuilder.builderService(NewsService::class.java)
    val filters = HashMap<String, String>()
    filters["q"] = location
    filters["apiKey"] = API_KEY

    val newsReportRequest = newsService.getNewsUpdates(filters)
    newsReportRequest.enqueue(object : Callback<NewsReport> {
        override fun onFailure(call: Call<NewsReport>, t: Throwable) {
            Log.d("CRASH", t.message.toString())
        }

        override fun onResponse(
            call: Call<NewsReport>,
            response: Response<NewsReport>
        ) {
            val newsReport: NewsReport = response.body()!!
            recyclerView.adapter = NewsAdapter(context, newsReport.articles)
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.isRefreshing = false
            }
        }

    })
}
