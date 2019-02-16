package com.example.newswatchtower5.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.newswatchtower5.R
import com.example.newswatchtower5.constants.NEWS_DETAILS
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.models.Article
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_news_details, container, false)

        val article = this.arguments!![NEWS_DETAILS] as Article

        val homeButton = view.findViewById<ImageButton>(R.id.home_button)
        val shareButton = view.findViewById<FloatingActionButton>(R.id.floatingActionShareButton)
        val newsImage = view.findViewById<ImageView>(R.id.newsImage)
        val headlineTextView = view.findViewById<TextView>(R.id.headlineTextView)
        val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val publishDateTextView = view.findViewById<TextView>(R.id.publishDateTextView)
        val sourceTextView = view.findViewById<TextView>(R.id.sourceTextView)
        val authorTextView = view.findViewById<TextView>(R.id.authorTextView)
        val sourceUrlTextView = view.findViewById<TextView>(R.id.sourceUrlTextView)

        Picasso.with(view.context).load(article.urlToImage).into(newsImage)
        val simpleDateFormat = SimpleDateFormat("d-MM-Y", Locale.US)
        val date = simpleDateFormat.parse(article.publishedAt.substring(0, 10))
        headlineTextView.text = article.title
        descriptionTextView.text = article.description
        publishDateTextView.text = simpleDateFormat.format(date).replace('-', '/')
        sourceTextView.text = article.source.name
        authorTextView.text = article.author
        sourceUrlTextView.text = article.url

        homeButton.setOnClickListener {
            val helperInterface = activity as HelperInterface
            helperInterface.loadDefaultFragment()
        }

        shareButton.setOnClickListener {
            val message =
                "Title:\t" + article.title + "\n" + "\nDescription:\t" + article.description + "\n" + "\nLink:\t" + article.url +
                        "\n" + "\nSource:\t" + article.source.name + "\n"
            shareStory(view.context, message, view.context.packageManager)
        }


        return view
    }
}
