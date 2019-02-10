package com.example.newswatchtower5.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Responsible for handling the tab navigation.
 */
class NavigationTabAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    private var navigationTabList: MutableList<Fragment> = mutableListOf()

    override fun getItem(position: Int): Fragment = navigationTabList[position]

    override fun getCount(): Int = navigationTabList.size

    /**
     * Adds new fragments to tab list.
     */
    fun addFragment(fragment: Fragment) {
        navigationTabList.add(fragment)
    }
}
