package com.example.newswatchtower5.newsviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newswatchtower5.R
import com.example.newswatchtower5.adapters.NewsAdapter
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.models.NewsViewModal
import com.example.newswatchtower5.shared.backHomeClick

class InternationalNewsFragment : Fragment() {
    private lateinit var newsViewModal: NewsViewModal
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_shared, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)

        swipeRefreshLayout.setOnRefreshListener {
            loadNews(recyclerView)
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = LinearLayoutManager(this.context)

        loadNews(recyclerView)

        val homeButton = view.findViewById<ImageButton>(R.id.home_button)
        backHomeClick(activity as HelperInterface, homeButton)
        return view
    }

    private fun loadNews(recyclerView: RecyclerView) {
        newsViewModal = ViewModelProviders.of(this).get(NewsViewModal::class.java)
        newsViewModal.loadNewsArticle(
            this.context!!,
            Pair(getString(R.string.news_source), getString(R.string.bbc))
        )
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
