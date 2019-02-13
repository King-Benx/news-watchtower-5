package com.example.newswatchtower5.shared

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newswatchtower5.adapters.NewsAdapter
import com.example.newswatchtower5.constants.API_KEY
import com.example.newswatchtower5.models.FragmentTag
import com.example.newswatchtower5.models.NewsReport
import com.example.newswatchtower5.services.NewsService
import com.example.newswatchtower5.services.NewsServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var mFragments = ArrayList<String>()
val fragments = ArrayList<FragmentTag>()
var exitCount = 0

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

    val newsReportRequest = newsService.getNewsUpdates(filters)
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
}

/**
 * Handles rendering of fragments
 */
fun loadFragment(
    fragmentManager: FragmentManager,
    framelayout: FrameLayout,
    fragment: HashMap<String, Fragment>
) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    for (key in fragment.keys) {
        if (!mFragments.contains(key)) {
            fragmentTransaction.add(framelayout.id, fragment[key]!!, key)
            mFragments.add(key)
            fragments.add(FragmentTag(fragment[key]!!, key))
        } else {
            mFragments.remove(key)
            mFragments.add(key)
        }
        setFragmentVisibility(key, fragmentManager)
    }
    fragmentTransaction.commit()
}


/**
 * Handle fragment visibility
 */
fun setFragmentVisibility(tag: String, fragmentManager: FragmentManager) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    for (fragmentTag in fragments) {
        if (tag == fragmentTag.tag) {
            fragmentTransaction.show(fragmentTag.fragment)

        } else {
            fragmentTransaction.hide(fragmentTag.fragment)
        }
    }
    fragmentTransaction.commit()
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
