package com.sedatkavak.kriptak.screens.news_fragment

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.adapter.NewsAdapter
import com.sedatkavak.kriptak.api.model.Article
import com.sedatkavak.kriptak.api.service.NewsApiService
import com.sedatkavak.kriptak.api.service.NewsApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentNewsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsDataUpdater(
    private val binding : FragmentNewsBinding? = null,
    private val lifecycleScope: CoroutineScope,
    private val context: Context
) {
    fun fetchData(document : String = "apiTrKey") {
        fetchApiKeyFromFirestore(document, binding!!.newsProgressBar, binding.newsLoadingFrameLayout,binding.newsRecyclerView)
    }
    fun fetchApiKeyFromFirestore(document: String, progressBar: ProgressBar,progressBarFrameLayout: FrameLayout, recyclerView: RecyclerView) {
        val db = FirebaseFirestore.getInstance()
        db.collection("NewsApi").document(document)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val apiKey = document.getString("key")
                    val searchQuery = document.getString("searchQuery")
                    val language = document.getString("language")
                    val newsSize = document.getLong("newsSize")!!.toInt()
                    getNews(apiKey, searchQuery, language, newsSize, progressBar,progressBarFrameLayout, recyclerView)
                } else {
                }
            }
            .addOnFailureListener { exception ->
            }
    }
    fun getNews(apiKey: String?, searchQuery: String?, language: String?, newsSize: Int, progressBar: ProgressBar, progressBarFrameLayout: FrameLayout, recyclerView: RecyclerView) {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = NewsApiUtilities.getInstance().create(NewsApiService::class.java)
                .getNews(apiKey = apiKey!!, searchQuery = searchQuery!!, language = language!!)
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.GONE
                progressBarFrameLayout.visibility = View.GONE
                res.body()?.articles?.let { articles ->
                    val uniqueArticles = removeDuplicateArticles(articles)
                    recyclerView.adapter = NewsAdapter(uniqueArticles, newsSize)
                    val layoutManager = LinearLayoutManager(context)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.isNestedScrollingEnabled = false
                }
            }
        }
    }
    private fun removeDuplicateArticles(articles: List<Article>): List<Article> {
        val uniqueArticleList = mutableListOf<Article>()
        val titleSet = mutableSetOf<String>()
        for (article in articles) {
            if (!titleSet.contains(article.title)) {
                titleSet.add(article.title)
                uniqueArticleList.add(article)
            }
        }
        return uniqueArticleList
    }
}