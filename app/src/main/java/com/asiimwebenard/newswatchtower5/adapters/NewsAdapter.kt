package com.asiimwebenard.newswatchtower5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiimwebenard.newswatchtower5.R
import com.asiimwebenard.newswatchtower5.helpers.HelperInterface
import com.asiimwebenard.newswatchtower5.models.Article
import com.asiimwebenard.newswatchtower5.shared.handleSaveArticleClick
import com.asiimwebenard.newswatchtower5.shared.handleShareClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.RxView
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit

class NewsAdapter(context: Context, private val newsUpdates: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.single_news_item, parent, false))
    }

    override fun getItemCount(): Int = newsUpdates.size

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val newsUpdate = newsUpdates[position]
        with(newsUpdate) {
            holder.title.text = title
            holder.description.text = description
            val message =
                "Title:\t" + title + "\n" + "\nDescription:\t" + description + "\n" + "\nLink:\t" + url +
                        "\n" + "\nSource:\t" + source.name + "\n"
            handleShareClick(
                holder.itemView.context,
                holder.itemView.context.packageManager,
                holder.shareButton,
                message
            )

            Picasso.with(holder.itemView.context).load(urlToImage).into(holder.imageView)
            handleStoryClick(
                holder.itemView,
                holder.itemView.context as HelperInterface,
                newsUpdate
            )
        }



        handleSaveArticleClick(
            holder.itemView.context as HelperInterface,
            holder.saveButton,
            newsUpdate
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.newsImage)!!
        var title = itemView.findViewById<TextView>(R.id.headlineTextView)!!
        var description = itemView.findViewById<TextView>(R.id.descriptionTextView)!!
        var shareButton =
            itemView.findViewById<FloatingActionButton>(R.id.floatingActionShareButton)!!
        var saveButton =
            itemView.findViewById<FloatingActionButton>(R.id.floatingActionSaveButton)!!

    }

    /**
     * Handles story clicks
     */
    private fun handleStoryClick(view: View, helperInterface: HelperInterface, article: Article) {
        RxView.clicks(view).map {
            helperInterface.loadDetailView(article)
        }.throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe()
    }

}
