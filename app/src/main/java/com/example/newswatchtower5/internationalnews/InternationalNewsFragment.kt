package com.example.newswatchtower5.internationalnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newswatchtower5.R
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.shared.loadDataBySource

class InternationalNewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_shared, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        loadDataBySource(view.context, recyclerView, getString(R.string.bbc), null)

        swipeRefreshLayout.setOnRefreshListener {
            loadDataBySource(
                view.context,
                recyclerView,
                getString(R.string.bbc),
                swipeRefreshLayout
            )
        }
        val homeButton = view.findViewById<ImageButton>(R.id.home_button)
        homeButton.setOnClickListener {
            val helperInterface = activity as HelperInterface
            helperInterface.loadDefaultFragment()
        }
        return view
    }
}
