package com.sedatkavak.kriptak.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sedatkavak.kriptak.R
import com.sedatkavak.kriptak.api.model.CryptoCurrency
import com.sedatkavak.kriptak.databinding.HomepageDailyFavoriteItemLayoutBinding

class MarketAdapter(var context: Context, var list: List<CryptoCurrency>, var type: String) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    inner class MarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = HomepageDailyFavoriteItemLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.homepage_daily_favorite_item_layout, parent, false)
        )
    }

    fun updateData(dataItem: List<CryptoCurrency>) {
        list = dataItem
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = list[position]
        holder.binding.dailyFavoriteCryptoNameTextView.text = item.name
        holder.binding.dailyFavoriteCryptoSymbolTextView.text = item.symbol


        holder.binding.dailyFavoriteCryptoPriceTextView.text =
            "${String.format("%.02f", item.quotes[0].price)} $"


        if (item.quotes!![0].percentChange24h > 0) {
            holder.binding.dailyFavoriteCryptoChangeTextView.setTextColor(
                context.resources.getColor(
                    R.color.green
                )
            )
            holder.binding.dailyFavoriteCryptoChangeTextView.text =
                "+${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        } else {
            holder.binding.dailyFavoriteCryptoChangeTextView.setTextColor(
                context.resources.getColor(
                    R.color.red
                )
            )
            holder.binding.dailyFavoriteCryptoChangeTextView.text =
                "${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        }

        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}