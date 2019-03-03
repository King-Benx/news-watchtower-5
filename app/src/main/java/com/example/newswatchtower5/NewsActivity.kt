package com.example.newswatchtower5

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import com.example.newswatchtower5.constants.NEWS_DETAILS
import com.example.newswatchtower5.dao.StoredArticle
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.internationalnews.InternationalNewsFragment
import com.example.newswatchtower5.localenews.LocaleNewsFragment
import com.example.newswatchtower5.models.Article
import com.example.newswatchtower5.savedarticles.SavedArticleFragment
import com.example.newswatchtower5.shared.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.app_bar_news.*

class NewsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    HelperInterface {
    override fun deleteSavedArticle(storedArticle: StoredArticle) {
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
        alertDialog.setTitle(getString(R.string.confirm_delete))
        alertDialog.setMessage(storedArticle.title + getString(R.string.delete_message))
        alertDialog.setPositiveButton("Ok") { _, _ ->
            articleViewModal.deleteArticle(storedArticle)
        }
        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.create()
        alertDialog.show()
    }

    override fun loadSavedArticles() {
        val fragment = Pair(getString(R.string.savedArticleFragment), SavedArticleFragment())
        loadFragment(supportFragmentManager, findViewById(R.id.frame), fragment)
    }

    override fun progressStatus(status: Boolean) {
        if (status) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun storeArticle(storedArticle: StoredArticle) {
        articleViewModal.storeArticle(storedArticle)
        Toast.makeText(
            this,
            storedArticle.title + " " + getString(R.string.saved_successfully),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun loadDetailView(article: Article) {
        val detailFragment = DetailFragment()
        if (mFragments.contains(getString(R.string.detailFragment))) supportFragmentManager.beginTransaction().remove(
            detailFragment
        ).commitNowAllowingStateLoss()
        val bundle = Bundle()
        bundle.putParcelable(NEWS_DETAILS, article)
        detailFragment.arguments = bundle
        val fragment = Pair(getString(R.string.detailFragment), detailFragment)
        loadFragment(supportFragmentManager, findViewById(R.id.frame), fragment)
    }


    override fun loadDefaultFragment() {
        if (!checkInternetConnection(this)) this.progressStatus(true)
        init()
    }

    private lateinit var articleViewModal: ArticleViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (checkInternetConnection(this)) {
            init()
        } else {
            showInternetAlertDialiog(this)
        }

        articleViewModal = ViewModelProviders.of(this).get(ArticleViewModal::class.java)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        val backStackCount = mFragments.size
        if (backStackCount > 1) {
            val topFragmentTag = mFragments[backStackCount - 1]
            val newFragmentTag = mFragments[backStackCount - 2]
            setFragmentVisibility(newFragmentTag, supportFragmentManager)
            mFragments.remove(topFragmentTag)
        } else if (backStackCount == 1) {
            Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show()
            exitCount++
        }

        if (exitCount >= 2) {
            mFragments.clear()
            fragments.clear()
            super.onBackPressed()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
//            item.isChecked = false

            R.id.home -> {
                init()
            }
            R.id.international_news -> {
                val fragment =
                    Pair(getString(R.string.internationalFragment), InternationalNewsFragment())
                loadFragment(supportFragmentManager, findViewById(R.id.frame), fragment)
            }
            R.id.stored_news -> loadSavedArticles()
            R.id.locale_news -> {
                val fragment =
                    Pair(getString(R.string.localeNewsFragment), LocaleNewsFragment())
                loadFragment(supportFragmentManager, findViewById(R.id.frame), fragment)
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    fun init() {
        val fragment = Pair(getString(R.string.generalFragment), GeneralFragment())
        loadFragment(supportFragmentManager, findViewById(R.id.frame), fragment)
    }

}
