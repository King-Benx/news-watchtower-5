package com.asiimwebenard.newswatchtower5.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.asiimwebenard.newswatchtower5.R
import com.asiimwebenard.newswatchtower5.constants.NEWS_DETAILS
import com.asiimwebenard.newswatchtower5.helpers.HelperInterface
import com.asiimwebenard.newswatchtower5.models.Article
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
        val saveButton = view.findViewById<FloatingActionButton>(R.id.floatingActionSaveButton)
        val newsImage = view.findViewById<ImageView>(R.id.newsImage)
        val headlineTextView = view.findViewById<TextView>(R.id.headlineTextView)
        val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val publishDateTextView = view.findViewById<TextView>(R.id.publishDateTextView)
        val sourceTextView = view.findViewById<TextView>(R.id.sourceTextView)
        val authorTextView = view.findViewById<TextView>(R.id.authorTextView)
        val sourceUrlTextView = view.findViewById<TextView>(R.id.sourceUrlTextView)

        with(article) {
            Picasso.with(view.context).load(urlToImage).into(newsImage)
            val simpleDateFormat = SimpleDateFormat("d-MM-Y", Locale.US)
            val date = simpleDateFormat.parse(publishedAt.substring(0, 10))
            headlineTextView.text = title
            descriptionTextView.text = description
            publishDateTextView.text = simpleDateFormat.format(date).replace('-', '/')
            sourceTextView.text = source.name
            authorTextView.text = author
            sourceUrlTextView.text = url

            val helperInterface = activity as HelperInterface
            helperInterface.progressStatus(false)

            val message =
                "Title:\t" + title + "\n" + "\nDescription:\t" + description + "\n" + "\nLink:\t" + url +
                        "\n" + "\nSource:\t" + source.name + "\n"
            handleShareClick(view.context, view.context.packageManager, shareButton, message)
        }

        handleSaveArticleClick(activity as HelperInterface, saveButton, article)
        backHomeClick(activity as HelperInterface, homeButton)
        return view
    }
}
