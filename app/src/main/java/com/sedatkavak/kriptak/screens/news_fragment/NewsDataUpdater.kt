package com.sedatkavak.kriptak.screens.news_fragment

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sedatkavak.kriptak.adapter.NewsAdapter
import com.sedatkavak.kriptak.api.model.Article
import com.sedatkavak.kriptak.api.service.NewsApiService
import com.sedatkavak.kriptak.api.service.NewsApiUtilities
import com.sedatkavak.kriptak.databinding.FragmentNewsBinding
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionActivity
import com.sedatkavak.kriptak.screens.connection_screen.ConnectionUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsDataUpdater(
    private val binding: FragmentNewsBinding? = null,
    private val lifecycleScope: CoroutineScope,
    private val context: Context,
) {
    fun fetchData(document: String = "apiTrKey") {
        if (!ConnectionUtils.isNetworkAvailable(context)) {
            val intent = Intent(context, ConnectionActivity::class.java)
            context.startActivity(intent)
            return
        }
        fetchApiKeyFromFirestore(
            document,
            UIComponents(
                binding!!.newsProgressBar,
                binding.newsLoadingFrameLayout,
                binding.newsRecyclerView,
            )
        )
    }

    fun fetchApiKeyFromFirestore(
        document: String,
        uiComponents: UIComponents,
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection("NewsApi").document(document)
            .get()
            .addOnSuccessListener { documentResult ->
                if (documentResult != null) {
                    val newsRequest = NewsRequest(
                        apiKey = documentResult.getString("key")!!,
                        searchQuery = documentResult.getString("searchQuery")!!,
                        language = documentResult.getString("language")!!,
                        newsSize = documentResult.getLong("newsSize")!!.toInt(),
                        unwantedSources = documentResult.get("unwantedSources") as? List<String>
                            ?: emptyList()
                    )
                    getNews(newsRequest, uiComponents)
                }
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun getNews(
        newsRequest: NewsRequest,
        uiComponents: UIComponents,
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = NewsApiUtilities.getInstance().create(NewsApiService::class.java)
                .getNews(
                    apiKey = newsRequest.apiKey,
                    searchQuery = newsRequest.searchQuery,
                    language = newsRequest.language
                )
            withContext(Dispatchers.Main) {
                uiComponents.progressBar.visibility = View.GONE
                uiComponents.progressBarFrameLayout.visibility = View.GONE
                res.body()?.articles?.let { articles ->
                    val uniqueArticles = filterNews(articles, newsRequest.unwantedSources)
                    uiComponents.recyclerView.adapter =
                        NewsAdapter(uniqueArticles, newsRequest.newsSize)
                    val layoutManager = LinearLayoutManager(context)
                    uiComponents.recyclerView.layoutManager = layoutManager
                    uiComponents.recyclerView.isNestedScrollingEnabled = false
                }
            }
        }
    }

    private fun filterNews(articles: List<Article>, unwantedSources: List<String>): List<Article> {
        val uniqueArticleList = mutableListOf<Article>()
        val titleSet = mutableSetOf<String>()
        for (article in articles) {
            if (!titleSet.contains(article.title) && !unwantedSources.contains(article.source.name)) {
                titleSet.add(article.title)
                uniqueArticleList.add(article)
            }
        }
        return uniqueArticleList
    }
}

data class NewsRequest(
    val apiKey: String,
    val searchQuery: String,
    val language: String,
    val newsSize: Int,
    val unwantedSources: List<String>
)

data class UIComponents(
    val progressBar: ProgressBar,
    val progressBarFrameLayout: FrameLayout,
    val recyclerView: RecyclerView
)
