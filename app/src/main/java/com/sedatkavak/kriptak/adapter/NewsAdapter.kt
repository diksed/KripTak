package com.sedatkavak.kriptak.adapter

import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.sedatkavak.kriptak.api.model.Article
import com.sedatkavak.kriptak.databinding.HomepageNewsItemLayoutBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter(private val articles: List<Article>, private val itemCount: Int) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HomepageNewsItemLayoutBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    inner class NewsViewHolder(private val binding: HomepageNewsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.dailyNewsTitleTextView.text = article.title
            val dateTimeString = article.publishedAt
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date = inputFormat.parse(dateTimeString)

            val dateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
            val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())

            val formattedDate = dateFormat.format(date!!)
            val formattedYear = yearFormat.format(date)
            val formattedTime = timeFormat.format(date)
            val htmlText = "$formattedDate<br/>$formattedYear"
            binding.newsDateTextView.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
            binding.newsDateTimeTextView.text = formattedTime



            Picasso.get()
                .load(article.urlToImage)
                .resize(300, 300)
                .into(binding.dailyNewsImageView)

            binding.root.setOnClickListener {
                val context = binding.root.context
                val url = article.url
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
        }
    }
}
