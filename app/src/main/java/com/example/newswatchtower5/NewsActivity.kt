package com.example.newswatchtower5

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.newswatchtower5.adapters.NavigationTabAdapter
import com.example.newswatchtower5.constants.*
import com.example.newswatchtower5.kampalanews.KampalaNewsFragment
import com.example.newswatchtower5.kigalinews.KigaliNewsFragment
import com.example.newswatchtower5.lagosnews.LagosNewsFragment
import com.example.newswatchtower5.nairobinews.NairobiNewsFragment
import com.example.newswatchtower5.newyorknews.NewYorkNewsFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.app_bar_news.*
import kotlinx.android.synthetic.main.content_news.*

class NewsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbar)
        setUpViewPager()
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


    }

    /**
     * Connects the Navigation Tabs to the respective Fragments.
     */
    private fun setUpViewPager() {
        val navigationTabAdapter = NavigationTabAdapter(supportFragmentManager)
        navigationTabAdapter.addFragment(KampalaNewsFragment())
        navigationTabAdapter.addFragment(KigaliNewsFragment())
        navigationTabAdapter.addFragment(LagosNewsFragment())
        navigationTabAdapter.addFragment(NairobiNewsFragment())
        navigationTabAdapter.addFragment(NewYorkNewsFragment())
        viewpager_container.adapter = navigationTabAdapter
        tab_buttons.setupWithViewPager(viewpager_container)
        tab_buttons.getTabAt(KAMPALA_NEWS)!!.text = getString(R.string.kampala)
        tab_buttons.getTabAt(KIGALI_NEWS)!!.text = getString(R.string.kigali)
        tab_buttons.getTabAt(LAGOS_NEWS)!!.text = getString(R.string.lagos)
        tab_buttons.getTabAt(NAIROBI_NEWS)!!.text = getString(R.string.nairobi)
        tab_buttons.getTabAt(NEW_YORK_NEWS)!!.text = getString(R.string.new_york)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
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
            //TODO: Add some items
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
