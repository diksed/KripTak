package com.sedatkavak.kriptak.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.api.model.CryptoCurrency
import com.sedatkavak.kriptak.databinding.HomepageDailyFavoriteItemLayoutBinding

class TopMarketAdapter(var context: Context, val list: List<CryptoCurrency>) :
    RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>() {

    inner class TopMarketViewHolder(val binding: HomepageDailyFavoriteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
        val binding = HomepageDailyFavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopMarketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
        val item = list[position]
        val itemBinding = holder.binding

        itemBinding.dailyFavoriteCryptoNameTextView.text = item.name
        itemBinding.dailyFavoriteCryptoSymbolTextView.text = item.symbol
        itemBinding.dailyFavoriteCryptoPriceTextView.text =
            "${String.format("%.02f", item.quotes[0].price)} $"

        Glide.with(context).load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
        ).thumbnail(Glide.with(context).load(android.R.drawable.gallery_thumb))
            .into(itemBinding.dailyFavoriteCryptoImageView)

        Glide.with(context).load(
            "https://s3.coinmarketcap.com/generated/sparklines/web/1d/usd/" + item.id + ".png"
        ).thumbnail(Glide.with(context).load(android.R.drawable.gallery_thumb))
            .into(itemBinding.dailyFavoriteCryptoChartImageView)

        if (item.quotes!![0].percentChange24h > 0) {
            itemBinding.dailyFavoriteCryptoChangeImageView.setImageResource(R.drawable.baseline_arrow_drop_up_24)
            itemBinding.dailyFavoriteCryptoChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            itemBinding.dailyFavoriteCryptoChangeTextView.text = "%${String.format("%.02f", item.quotes[0].percentChange24h)}"
        } else {
            itemBinding.dailyFavoriteCryptoChangeImageView.setImageResource(R.drawable.baseline_arrow_drop_down_24)
            itemBinding.dailyFavoriteCryptoChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            val percentChange = item.quotes[0].percentChange24h
            val formattedPercentChange = String.format("%.02f", percentChange).replace("-", "")
            itemBinding.dailyFavoriteCryptoChangeTextView.text = "%$formattedPercentChange"
        }

        holder.itemView.setOnClickListener {
            // Click event işlemleri
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
