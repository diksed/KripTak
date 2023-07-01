package com.sedatkavak.kriptak.screens.news_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var btnTurkishNews: AppCompatButton
    private lateinit var btnEnglishNews: AppCompatButton
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsDataUpdater: NewsDataUpdater

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root

        btnTurkishNews = binding.btnTurkishNews
        btnEnglishNews = binding.btnEnglishNews
        newsRecyclerView = binding.newsRecyclerView

        newsDataUpdater = NewsDataUpdater(binding, viewLifecycleOwner.lifecycleScope, requireContext())

        btnTurkishNews.isEnabled = false
        btnTurkishNews.background = resources.getDrawable(R.drawable.news_language_deactive_button, null)
        btnEnglishNews.isEnabled = true

        btnTurkishNews.setOnClickListener {
            newsDataUpdater.fetchData()
            btnTurkishNews.background = resources.getDrawable(R.drawable.news_language_deactive_button, null)
            btnTurkishNews.isEnabled = false
            btnEnglishNews.background = resources.getDrawable(R.drawable.news_language_active_button, null)
            btnEnglishNews.isEnabled = true
        }
        btnEnglishNews.setOnClickListener {
            newsDataUpdater.fetchData(document = "apiKey")
            btnEnglishNews.isEnabled = false
            btnEnglishNews.background = resources.getDrawable(R.drawable.news_language_deactive_button, null)
            btnTurkishNews.background = resources.getDrawable(R.drawable.news_language_active_button, null)
            btnTurkishNews.isEnabled = true
        }

        newsDataUpdater.fetchData()

        return view
    }
}


