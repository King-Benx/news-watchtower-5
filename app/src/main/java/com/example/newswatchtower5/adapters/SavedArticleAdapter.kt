package com.example.newswatchtower5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newswatchtower5.R
import com.example.newswatchtower5.dao.StoredArticle
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.models.Article
import com.example.newswatchtower5.models.Source
import com.example.newswatchtower5.shared.checkInternetConnection
import com.example.newswatchtower5.shared.handleShareClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.RxView
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit

class SavedArticleAdapter(context: Context, private val storedArticles: List<StoredArticle>) :
    RecyclerView.Adapter<SavedArticleAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedArticleAdapter.ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.single_saved_news_item, parent, false))
    }

    override fun getItemCount(): Int = storedArticles.size

    override fun onBindViewHolder(holder: SavedArticleAdapter.ViewHolder, position: Int) {
        val storedArticle = storedArticles[position]
        with(storedArticle) {
            holder.description.text = description
            holder.title.text = title
            val message =
                "Title:\t" + title + "\n" + "\nDescription:\t" + description + "\n" + "\nLink:\t" + url +
                        "\n" + "\nSource:\t" + source + "\n"

            handleShareClick(
                holder.itemView.context,
                holder.itemView.context.packageManager,
                holder.shareButton,
                message
            )

            handleDeleteClick(
                holder.deleteButton,
                holder.itemView.context as HelperInterface,
                storedArticle
            )

            when {
                checkInternetConnection(holder.itemView.context) -> Picasso.with(holder.itemView.context).load(
                    urlToImage
                ).into(holder.imageView)
                else -> Picasso.with(holder.imageView.context).load(R.drawable.default_image).into(
                    holder.imageView
                )
            }

            val article = Article(
                Source(null, source),
                author,
                title,
                description,
                url,
                urlToImage,
                publishedAt
            )

            handleStoryClick(holder.itemView, holder.itemView.context as HelperInterface, article)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.newsImage)
        var title = itemView.findViewById<TextView>(R.id.headlineTextView)
        var description = itemView.findViewById<TextView>(R.id.descriptionTextView)
        var shareButton =
            itemView.findViewById<FloatingActionButton>(R.id.floatingActionShareButton)
        var deleteButton =
            itemView.findViewById<FloatingActionButton>(R.id.floatingActionDeleteButton)

    }

    /**
     * Handle saved story clicks.
     */
    private fun handleStoryClick(view: View, helperInterface: HelperInterface, article: Article) {
        RxView.clicks(view).map {
            helperInterface.loadDetailView(article)
        }.throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe()
    }

    /**
     * Handle delete click
     */
    private fun handleDeleteClick(
        button: FloatingActionButton,
        helperInterface: HelperInterface,
        storedArticle: StoredArticle
    ) {
        RxView.clicks(button).map {
            helperInterface.deleteSavedArticle(storedArticle)
        }.throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe()

    }
}
