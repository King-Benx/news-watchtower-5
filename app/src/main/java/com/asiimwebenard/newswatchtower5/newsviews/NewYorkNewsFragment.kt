package com.asiimwebenard.newswatchtower5.newsviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.asiimwebenard.newswatchtower5.R
import com.asiimwebenard.newswatchtower5.adapters.NewsAdapter
import com.asiimwebenard.newswatchtower5.helpers.HelperInterface
import com.asiimwebenard.newswatchtower5.models.NewsViewModal

class NewYorkNewsFragment : Fragment() {

    private lateinit var newsViewModal: NewsViewModal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.news_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)

        swipeRefreshLayout.setOnRefreshListener {
            loadNews(recyclerView)
            swipeRefreshLayout.isRefreshing = false
        }

        loadNews(recyclerView)

        return view
    }

    private fun loadNews(recyclerView: RecyclerView) {
        newsViewModal = ViewModelProviders.of(this).get(NewsViewModal::class.java)
        newsViewModal.loadNewsArticle(this.context!!, Pair(getString(R.string.location), getString(R.string.america)))
            ?.observe(this, Observer { newsReport ->
                newsReport?.let {
                    recyclerView.adapter = NewsAdapter(this.context!!, newsReport.articles).also {
                        val helperInterface = this.context as HelperInterface
                        helperInterface.progressStatus(false)
                    }
                }
            })
    }
}
