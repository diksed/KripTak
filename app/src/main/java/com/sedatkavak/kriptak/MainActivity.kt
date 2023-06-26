package com.sedatkavak.kriptak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sedatkavak.kriptak.databinding.ActivityMainBinding
import com.sedatkavak.kriptak.screens.crypto_list_fragment.CryptoListFragment
import com.sedatkavak.kriptak.screens.favorites_fragment.FavoritesFragment
import com.sedatkavak.kriptak.screens.home_fragment.HomeFragment
import com.sedatkavak.kriptak.screens.news_fragment.NewsFragment
import com.sedatkavak.kriptak.screens.settings_fragment.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationBar.setOnItemSelectedListener {

            when(it.itemId){
                R.id.menu_home -> replaceFragment(HomeFragment())
                R.id.menu_favorites -> replaceFragment(FavoritesFragment())
                R.id.menu_crypto_list -> replaceFragment(CryptoListFragment())
                R.id.menu_news -> replaceFragment(NewsFragment())
                R.id.menu_settings -> replaceFragment(SettingsFragment())
                else ->{
                }
            }
            true

        }
    }
    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentLayout,fragment)
        fragmentTransaction.commit()


    }
}