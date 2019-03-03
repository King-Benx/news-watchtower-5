package com.example.newswatchtower5.savedarticles

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
import com.example.newswatchtower5.R
import com.example.newswatchtower5.adapters.SavedArticleAdapter
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.shared.ArticleViewModal
import com.example.newswatchtower5.shared.backHomeClick

class SavedArticleFragment : Fragment() {
    private lateinit var articleViewModal: ArticleViewModal
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_saved, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        articleViewModal = ViewModelProviders.of(this).get(ArticleViewModal::class.java)

        articleViewModal.storedArticles.observe(this, Observer { articles ->
            articles?.let {
                recyclerView.adapter = SavedArticleAdapter(view.context, articles)
            }
        }).also {
            val helperInterface = view.context as HelperInterface
            helperInterface.progressStatus(false)
        }

        val homeButton = view.findViewById<ImageButton>(R.id.home_button)
        backHomeClick(activity as HelperInterface, homeButton)
        return view
    }
}

