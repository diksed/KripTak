package com.sedatkavak.kriptak.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.api.model.CryptoCurrency
import com.sedatkavak.kriptak.databinding.HomepageDailyFavoriteItemLayoutBinding

class TopMarketAdapter(var context: Context, val list: List<CryptoCurrency>) :
    RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>() {

    inner class TopMarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = HomepageDailyFavoriteItemLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
        return TopMarketViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.homepage_daily_favorite_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
        val item = list[position]
        holder.binding.dailyFavoriteCryptoNameTextView.text = item.name
        holder.binding.dailyFavoriteCryptoSymbolTextView.text = item.symbol
        holder.binding.dailyFavoriteCryptoPriceTextView.text =
            "${String.format("%.02f", item.quotes[0].price)} $"

        Glide.with(context).load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
        ).thumbnail(Glide.with(context).load(android.R.drawable.stat_sys_download))
            .into(holder.binding.dailyFavoriteCryptoImageView)

        Glide.with(context).load(
            "https://s3.coinmarketcap.com/generated/sparklines/web/1d/usd/" + item.id + ".png"
        ).thumbnail(Glide.with(context).load(android.R.drawable.ic_menu_upload))
            .into(holder.binding.dailyFavoriteCryptoChartImageView)

        if (item.quotes!![0].percentChange24h > 0) {
            holder.binding.dailyFavoriteCryptoChangeImageView.setImageResource(R.drawable.baseline_arrow_drop_up_24)
            holder.binding.dailyFavoriteCryptoChangeTextView.setTextColor(
                context.resources.getColor(R.color.green))
            holder.binding.dailyFavoriteCryptoChangeTextView.text =
                "%${String.format("%.02f", item.quotes[0].percentChange24h)}"
        } else {
            holder.binding.dailyFavoriteCryptoChangeImageView.setImageResource(R.drawable.baseline_arrow_drop_down_24)
            holder.binding.dailyFavoriteCryptoChangeTextView.setTextColor(
                context.resources.getColor(R.color.red))
            val percentChange = item.quotes[0].percentChange24h
            val formattedPercentChange =
                String.format("%.02f", percentChange).replace("-", "")
            holder.binding.dailyFavoriteCryptoChangeTextView.text = "%$formattedPercentChange"
        }
        holder.itemView.setOnClickListener {
        }

    }
    override fun getItemCount(): Int {
        return 3
    }
}