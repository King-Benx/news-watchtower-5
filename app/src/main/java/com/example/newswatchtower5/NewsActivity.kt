package com.example.newswatchtower5

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.newswatchtower5.constants.NEWS_DETAILS
import com.example.newswatchtower5.helpers.HelperInterface
import com.example.newswatchtower5.internationalnews.InternationalNewsFragment
import com.example.newswatchtower5.models.Article
import com.example.newswatchtower5.shared.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.app_bar_news.*

class NewsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    HelperInterface {
    override fun loadDetailView(article: Article) {
        val detailFragment = DetailFragment()
        val bundle = Bundle()
        bundle.putParcelable(NEWS_DETAILS, article)
        detailFragment.arguments = bundle
        val fragment = HashMap<String, Fragment>()
        fragment[getString(R.string.detailFragment)] = detailFragment
        loadFragment(supportFragmentManager, findViewById(R.id.frame), fragment)
    }


    override fun loadDefaultFragment() {
        init()
    }


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

        init()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        val backStackCount = com.example.newswatchtower5.shared.mFragments.size
        if (backStackCount > 1) {
            val topFragmentTag = com.example.newswatchtower5.shared.mFragments[backStackCount - 1]
            val newFragmentTag = com.example.newswatchtower5.shared.mFragments[backStackCount - 2]
            setFragmentVisibility(newFragmentTag, supportFragmentManager)
            com.example.newswatchtower5.shared.mFragments.remove(topFragmentTag)
        } else if (backStackCount == 1) {
            exitCount++
            Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show()
        }

        if (exitCount >= 2) {
            com.example.newswatchtower5.shared.mFragments.clear()
            com.example.newswatchtower5.shared.mFragments = ArrayList()
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
                val fragment = HashMap<String, Fragment>()
                fragment[getString(R.string.internationalFragment)] = InternationalNewsFragment()
                loadFragment(supportFragmentManager, findViewById(R.id.frame), fragment)
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    fun init() {
        val fragment = HashMap<String, Fragment>()
        fragment[getString(R.string.generalFragment)] = GeneralFragment()
        loadFragment(supportFragmentManager, findViewById(R.id.frame), fragment)
    }

}
