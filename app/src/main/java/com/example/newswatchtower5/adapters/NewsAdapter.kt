package com.example.newswatchtower5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newswatchtower5.R
import com.example.newswatchtower5.models.Article

class NewsAdapter(context: Context, private val newsUpdates: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.single_news_item, parent, false))
    }

    override fun getItemCount(): Int = newsUpdates.size

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val newsUpdate = newsUpdates[position]
//      holder.imageUrl.setImageDrawable() = newsUpdate.urlToImage
        holder.title.text = newsUpdate.title
        holder.description.text = newsUpdate.description
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var imageUrl = itemView.findViewById<ImageView>(R.id.newsImage)
        var title = itemView.findViewById<TextView>(R.id.headlineTextView)
        var description = itemView.findViewById<TextView>(R.id.descriptionTextView)

    }
}
