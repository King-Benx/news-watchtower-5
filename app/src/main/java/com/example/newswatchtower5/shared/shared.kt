package com.example.newswatchtower5.shared

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.newswatchtower5.R
import com.example.newswatchtower5.dao.StoredArticle
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.models.Article
import com.example.newswatchtower5.models.FragmentTag
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.RxView
import java.util.*
import java.util.concurrent.TimeUnit

val mFragments = mutableListOf<String>()
val fragments = mutableListOf<FragmentTag>()
var exitCount = 0


/** THIS FILE CONTAINS ALL SHARED FUNCTIONALITY WITHIN THE APPLICATION */

/**
 * shows alert dialog when there is no internet
 */
fun showInternetAlertDialiog(context: Context) {
    val alertDialog = AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert)
    alertDialog.setTitle(context.getString(R.string.no_internet_title))
    alertDialog.setMessage(context.getString(R.string.internet_dialog_message))
    alertDialog.setPositiveButton(context.getString(R.string.ok)) { _, _ ->
        context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
    alertDialog.setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
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
        fragmentTransaction.commitAllowingStateLoss()
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
            fragmentTransaction.commitAllowingStateLoss()
        } else {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.hide(it.fragment)
            fragmentTransaction.commitAllowingStateLoss()
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
