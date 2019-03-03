package com.example.newswatchtower5.shared

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newswatchtower5.R
import com.example.newswatchtower5.adapters.NewsAdapter
import com.example.newswatchtower5.constants.API_KEY
import com.example.newswatchtower5.dao.StoredArticle
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.models.Article
import com.example.newswatchtower5.models.FragmentTag
import com.example.newswatchtower5.models.NewsReport
import com.example.newswatchtower5.services.NewsService
import com.example.newswatchtower5.services.NewsServiceBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.RxView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit

val mFragments = mutableListOf<String>()
val fragments = mutableListOf<FragmentTag>()
var exitCount = 0


/** THIS FILE CONTAINS ALL SHARED FUNCTIONALITY WITHIN THE APPLICATION */

/**
 * Handles retrieval of data basing on a location.
 */
fun loadData(
    context: Context,
    recyclerView: RecyclerView,
    location: String,
    swipeRefreshLayout: SwipeRefreshLayout? = null
) {
    val newsService = NewsServiceBuilder.builderService(NewsService::class.java)
    val filters = HashMap<String, String>()
    filters["q"] = location
    filters["apiKey"] = API_KEY

    if (checkInternetConnection(context)) {

        val newsReportRequest = newsService.getNewsUpdates(filters)

        newsReportRequest.enqueue(object : Callback<NewsReport> {
            override fun onFailure(call: Call<NewsReport>, t: Throwable) {
                Log.d("CRASH", t.message.toString())
            }

            override fun onResponse(
                call: Call<NewsReport>,
                response: Response<NewsReport>
            ) {
                val helperInterface = context as HelperInterface
                helperInterface.progressStatus(true)
                val newsReport: NewsReport = response.body()!!
                recyclerView.adapter = NewsAdapter(context, newsReport.articles)
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.isRefreshing = false
                }
                helperInterface.progressStatus(false)
            }

        })
    } else {
        showInternetAlertDialiog(context)
    }

}

/**
 * shows alert dialog when there is no internet
 */
fun showInternetAlertDialiog(context: Context) {
    val alertDialog = AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert)
    alertDialog.setTitle(context.getString(R.string.no_internet_title))
    alertDialog.setMessage(context.getString(R.string.internet_dialog_message))
    alertDialog.setPositiveButton("Ok") { _, _ ->
        context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
    alertDialog.setNegativeButton("Cancel") { dialog, _ ->
        if (!checkInternetConnection(context)) {
            val helperInterface = context as HelperInterface
            helperInterface.loadSavedArticles()
        }
        dialog.dismiss()
    }
    alertDialog.create()
    alertDialog.show()
}

/**
 * Handles retrieval data based on a source.
 */
fun loadDataBySource(
    context: Context,
    recyclerView: RecyclerView,
    source: String,
    swipeRefreshLayout: SwipeRefreshLayout? = null
) {
    val newsService = NewsServiceBuilder.builderService(NewsService::class.java)
    val filters = HashMap<String, String>()
    filters["sources"] = source
    filters["apiKey"] = API_KEY
    if (checkInternetConnection(context)) {
        val newsReportRequest = newsService.getTopHeadlines(filters)
        newsReportRequest.enqueue(object : Callback<NewsReport> {
            override fun onFailure(call: Call<NewsReport>, t: Throwable) {
                Log.d("CRASH", t.message.toString())
            }

            override fun onResponse(
                call: Call<NewsReport>,
                response: Response<NewsReport>
            ) {
                val newsReport: NewsReport = response.body()!!
                recyclerView.adapter = NewsAdapter(context, newsReport.articles)
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }

        })
    } else {
        showInternetAlertDialiog(context)
    }
}

/**
 * Handles rendering of fragments
 */
fun loadFragment(
    fragmentManager: FragmentManager,
    frameLayout: FrameLayout,
    fragment: Pair<String, Fragment>
) {

    if (!mFragments.contains(fragment.first)) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(frameLayout.id, fragment.second, fragment.first)
        fragmentTransaction.commit()
        mFragments.add(fragment.first)
        fragments.add(FragmentTag(fragment = fragment.second, tag = fragment.first))
    } else {
        mFragments.remove(fragment.first)
        mFragments.add(fragment.first)
    }
    setFragmentVisibility(tag = fragment.first, fragmentManager = fragmentManager)
}


/**
 * Handle fragment visibility
 */
fun setFragmentVisibility(tag: String, fragmentManager: FragmentManager) {

    fragments.forEach {
        if (tag == it.tag) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.show(it.fragment)
            fragmentTransaction.commit()
        } else {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.hide(it.fragment)
            fragmentTransaction.commit()
        }
    }
}

/**
 * Handles sharing of a user's stories.
 */
fun shareStory(context: Context, message: String, packageManger: PackageManager) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }
    if (sendIntent.resolveActivity(packageManger) != null) {
        context.startActivity(sendIntent)
    }
}

/**
 * Handles sharing of stories reactively.
 */
fun handleShareClick(
    context: Context,
    packageManager: PackageManager,
    button: FloatingActionButton,
    message: String
) {
    RxView.clicks(button).map {
        shareStory(context, message, packageManager)
    }.throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe()
}

/**
 * Handles saving of stories reactively
 */
fun handleSaveArticleClick(
    helperInterface: HelperInterface,
    button: FloatingActionButton,
    article: Article
) {
    with(article) {
        val storedArticle = StoredArticle(
            UUID.randomUUID().toString(),
            source.name,
            author,
            title,
            description,
            url,
            urlToImage,
            publishedAt
        )
        RxView.clicks(button).map {
            helperInterface.storeArticle(storedArticle)
        }.throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe()
    }
}

/**
 * Handles navigation to home button reactively.
 */
fun backHomeClick(helperInterface: HelperInterface, button: ImageButton) {
    RxView.clicks(button).map {
        helperInterface.loadDefaultFragment()
    }.throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe()
}


/**
 * Checks for network connectivity
 */
fun checkInternetConnection(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnected == true
}
