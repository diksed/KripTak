package com.sedatkavak.kriptak

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sedatkavak.kriptak.adapter.ViewPagerAdapter
import com.sedatkavak.kriptak.databinding.ActivityMainBinding
import com.sedatkavak.kriptak.utils.FragmentPositions.CRYPTO_LIST_FRAGMENT_POSITION
import com.sedatkavak.kriptak.utils.FragmentPositions.FAVORITES_FRAGMENT_POSITION
import com.sedatkavak.kriptak.utils.FragmentPositions.HOME_FRAGMENT_POSITION
import com.sedatkavak.kriptak.utils.FragmentPositions.NEWS_FRAGMENT_POSITION


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            hideSystemUI()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewPager.currentItem == HOME_FRAGMENT_POSITION) {
                    finish()
                } else {
                    viewPager.currentItem = HOME_FRAGMENT_POSITION
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        bottomNavigationView = binding.bottomNavigationBar
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        swipeViewPager()

    }

    private fun swipeViewPager() {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> viewPager.currentItem = HOME_FRAGMENT_POSITION
                R.id.menu_favorites -> viewPager.currentItem = FAVORITES_FRAGMENT_POSITION
                R.id.menu_crypto_list -> viewPager.currentItem = CRYPTO_LIST_FRAGMENT_POSITION
                R.id.menu_news -> viewPager.currentItem = NEWS_FRAGMENT_POSITION
            }
            true
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                bottomNavigationView.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Do nothing
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Do nothing
            }
        })
    }
}
