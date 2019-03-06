package com.example.newswatchtower5.newsviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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

class LocaleNewsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var location: String = ""
    private val recyclerView by lazy {
        view?.findViewById<RecyclerView>(R.id.recycler_view)
    }
    private lateinit var newsViewModal: NewsViewModal
    override fun onNothingSelected(parent: AdapterView<*>?) = Unit

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        loadNews(recyclerView!!, Pair("location", parent?.getItemAtPosition(position).toString()))
        location = parent?.getItemAtPosition(position).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_locale_news, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.continents_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)

        swipeRefreshLayout.setOnRefreshListener {
            loadNews(recyclerView, Pair(getString(R.string.location), location))
            swipeRefreshLayout.isRefreshing = false
        }


        return view
    }

    private fun loadNews(recyclerView: RecyclerView, pair: Pair<String, String>) {
        newsViewModal = ViewModelProviders.of(this).get(NewsViewModal::class.java)
        newsViewModal.loadNewsArticle(
            this.context!!, pair
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
