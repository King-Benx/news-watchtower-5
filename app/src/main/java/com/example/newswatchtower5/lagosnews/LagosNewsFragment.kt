package com.example.newswatchtower5.lagosnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newswatchtower5.R
import com.example.newswatchtower5.adapters.NewsAdapter
import com.example.newswatchtower5.constants.API_KEY
import com.example.newswatchtower5.models.NewsReport
import com.example.newswatchtower5.services.NewsService
import com.example.newswatchtower5.services.NewsServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LagosNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.news_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val newsService = NewsServiceBuilder.builderService(NewsService::class.java)
        val filters = HashMap<String, String>()
        filters["q"] = getString(R.string.nigeria)
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
                recyclerView.adapter = NewsAdapter(view.context, newsReport.articles)
            }

        })
        return view
    }
}
