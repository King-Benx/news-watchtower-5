package com.example.newswatchtower5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newswatchtower5.R
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.models.Article
import com.example.newswatchtower5.shared.shareStory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

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
            holder.shareButton.setOnClickListener {
                val message =
                    "Title:\t" + title + "\n" + "\nDescription:\t" + description + "\n" + "\nLink:\t" + url +
                            "\n" + "\nSource:\t" + source.name + "\n"
                shareStory(holder.itemView.context, message, holder.itemView.context.packageManager)


            }
            Picasso.with(holder.itemView.context).load(urlToImage).into(holder.imageView)
        }
        holder.itemView.setOnClickListener {
            val helperInterface = holder.itemView.context as HelperInterface
            helperInterface.loadDetailView(newsUpdate)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.newsImage)
        var title = itemView.findViewById<TextView>(R.id.headlineTextView)
        var description = itemView.findViewById<TextView>(R.id.descriptionTextView)
        var shareButton =
            itemView.findViewById<FloatingActionButton>(R.id.floatingActionShareButton)


    }
}
