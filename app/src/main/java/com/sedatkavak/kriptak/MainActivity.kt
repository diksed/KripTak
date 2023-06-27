package com.sedatkavak.kriptak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sedatkavak.kriptak.adapter.ViewPagerAdapter
import com.sedatkavak.kriptak.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        bottomNavigationView = binding.bottomNavigationBar
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        swipeViewPager()

    }
    private fun swipeViewPager() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> viewPager.currentItem = 0
                R.id.menu_favorites -> viewPager.currentItem = 1
                R.id.menu_crypto_list -> viewPager.currentItem = 2
                R.id.menu_news -> viewPager.currentItem = 3
                R.id.menu_settings -> viewPager.currentItem = 4
            }
            true
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                bottomNavigationView.menu.getItem(position).isChecked = true
            }
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        })
    }
}