package com.sedatkavak.kriptak.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sedatkavak.kriptak.screens.crypto_list_fragment.CryptoListFragment
import com.sedatkavak.kriptak.screens.favorites_fragment.FavoritesFragment
import com.sedatkavak.kriptak.screens.home_fragment.HomeFragment
import com.sedatkavak.kriptak.screens.news_fragment.NewsFragment
import com.sedatkavak.kriptak.utils.FragmentPositions.CRYPTO_LIST_FRAGMENT_POSITION
import com.sedatkavak.kriptak.utils.FragmentPositions.FAVORITES_FRAGMENT_POSITION
import com.sedatkavak.kriptak.utils.FragmentPositions.HOME_FRAGMENT_POSITION
import com.sedatkavak.kriptak.utils.FragmentPositions.NEWS_FRAGMENT_POSITION

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            HOME_FRAGMENT_POSITION -> HomeFragment()
            FAVORITES_FRAGMENT_POSITION -> FavoritesFragment()
            CRYPTO_LIST_FRAGMENT_POSITION -> CryptoListFragment()
            NEWS_FRAGMENT_POSITION -> NewsFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
