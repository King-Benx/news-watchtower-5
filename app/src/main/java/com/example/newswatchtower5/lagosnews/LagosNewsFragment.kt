package com.example.newswatchtower5.lagosnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newswatchtower5.R
import com.example.newswatchtower5.shared.loadData

class LagosNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.news_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        loadData(view.context, recyclerView, getString(R.string.nigeria), null)

        swipeRefreshLayout.setOnRefreshListener {
            loadData(view.context, recyclerView, getString(R.string.nigeria), swipeRefreshLayout)
        }

        return view
    }
}
