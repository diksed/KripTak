package com.sedatkavak.kriptak.screens.news_fragment

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.adapter.NewsAdapter
import com.sedatkavak.kriptak.api.service.NewsApiService
import com.sedatkavak.kriptak.api.service.NewsApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentNewsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsDataUpdater(
    private val binding: FragmentNewsBinding,
    private val lifecycleScope: CoroutineScope,
    private val context: Context
) {
    private val updateInterval: Long = 60000
    private val updateHandler = android.os.Handler()
    private val updateRunnable = object : Runnable {
        override fun run() {
            fetchData()
            updateHandler.postDelayed(this, updateInterval)
        }
    }

    fun startUpdating() {
        updateHandler.postDelayed(updateRunnable, updateInterval)
    }

    fun stopUpdating() {
        updateHandler.removeCallbacks(updateRunnable)
    }

    fun fetchData(document : String = "apiTrKey") {
        fetchApiKeyFromFirestore(document)
    }

    private fun fetchApiKeyFromFirestore(document: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("NewsApi").document(document)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val apiKey = document.getString("key")
                    val searchQuery = document.getString("searchQuery")
                    val language = document.getString("language")
                    val newsSize = document.getLong("newsSize")!!.toInt()
                    getNews(apiKey, searchQuery, language, newsSize)
                } else {
                }
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun getNews(apiKey: String?, searchQuery: String?, language: String?, newsSize : Int) {
        binding.newsProgressBar.setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.loading_animation))
        binding.newsProgressBar.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val res = NewsApiUtilities.getInstance().create(NewsApiService::class.java)
                .getNews(apiKey = apiKey!!, searchQuery = searchQuery!!, language = language!!)
            withContext(Dispatchers.Main) {
                binding.newsProgressBar.visibility = View.GONE
                res.body()?.articles?.let { articles ->
                    binding.newsRecyclerView.adapter = NewsAdapter(articles, newsSize)
                    val layoutManager = LinearLayoutManager(context)
                    binding.newsRecyclerView.layoutManager = layoutManager
                    binding.newsRecyclerView.isNestedScrollingEnabled = false
                }
            }
        }
    }
}