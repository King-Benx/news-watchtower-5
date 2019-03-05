package com.example.newswatchtower5.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.newswatchtower5.R
import com.example.newswatchtower5.adapters.NavigationTabAdapter
import com.example.newswatchtower5.constants.*
import com.example.newswatchtower5.newsviews.*
import com.google.android.material.tabs.TabLayout

class GeneralFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_general, container, false)
        setUpViewPager(view)
        return view
    }

    private fun setUpViewPager(view: View) {
        val navigationTabAdapter = NavigationTabAdapter(this.fragmentManager!!)
        val viewPager = view.findViewById<ViewPager>(R.id.viewpager_container)
        val tabButtons = view.findViewById<TabLayout>(R.id.tab_buttons)
        val fragments = listOf(
            KampalaNewsFragment(),
            KigaliNewsFragment(),
            LagosNewsFragment(),
            NairobiNewsFragment(),
            NewYorkNewsFragment()
        )
        fragments.forEach { navigationTabAdapter.addFragment(it) }
        viewPager.adapter = navigationTabAdapter
        tabButtons.setupWithViewPager(viewPager)
        tabButtons.getTabAt(KAMPALA_NEWS)!!.text = getString(R.string.kampala)
        tabButtons.getTabAt(KIGALI_NEWS)!!.text = getString(R.string.kigali)
        tabButtons.getTabAt(LAGOS_NEWS)!!.text = getString(R.string.lagos)
        tabButtons.getTabAt(NAIROBI_NEWS)!!.text = getString(R.string.nairobi)
        tabButtons.getTabAt(NEW_YORK_NEWS)!!.text = getString(R.string.new_york)

    }

}
