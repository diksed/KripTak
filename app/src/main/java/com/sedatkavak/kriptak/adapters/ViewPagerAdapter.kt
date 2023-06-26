package com.sedatkavak.kriptak.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sedatkavak.kriptak.screens.crypto_list_fragment.CryptoListFragment
import com.sedatkavak.kriptak.screens.favorites_fragment.FavoritesFragment
import com.sedatkavak.kriptak.screens.home_fragment.HomeFragment
import com.sedatkavak.kriptak.screens.news_fragment.NewsFragment
import com.sedatkavak.kriptak.screens.settings_fragment.SettingsFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> FavoritesFragment()
            2 -> CryptoListFragment()
            3 -> NewsFragment()
            4 -> SettingsFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
