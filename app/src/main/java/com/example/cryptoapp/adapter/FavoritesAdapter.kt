package com.example.cryptoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCryptoBinding
import com.example.cryptoapp.databinding.ItemFavouriteBinding
import com.example.cryptoapp.model.CryptoItem
import com.example.cryptoapp.room.CoinDatabase
import com.example.cryptoapp.viewmodel.FavouritesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesAdapter(context : Context,private val viewModel: FavouritesViewModel) : androidx.recyclerview.widget.ListAdapter<CryptoItem, FavoritesAdapter.CryptoHolder>(NoteDiffCallback) {
    private val coinDao = CoinDatabase(context).coinDao()
    var onCoinClick:(CryptoItem) -> Unit = {}

    object NoteDiffCallback : DiffUtil.ItemCallback<CryptoItem>() {
        override fun areItemsTheSame(oldItem: CryptoItem, newItem: CryptoItem): Boolean {
            return oldItem.coinId == newItem.coinId
        }

        override fun areContentsTheSame(oldItem: CryptoItem, newItem: CryptoItem): Boolean {
            return oldItem == newItem
        }
    }

    class CryptoHolder(val binding : ItemFavouriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val binding = ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.binding.tvCoinTitle.text = currentList[position].name
        holder.binding.tvCurrentPrice.text = "${currentList[position].currentPrice.toString()}$ "
        holder.binding.textView2.text = String.format("%%%.2f", currentList[position].priceChangePercentage24h)
        holder.binding.tvAth.text = "$${currentList[position].ath}"
        holder.binding.tvMarketCapRank.text = "${currentList[position].marketCap}"
        holder.binding.tvHigh24h.text = "$${currentList[position].high24h}"
        holder.binding.tvLow24h.text = "$${currentList[position].low24h}"


        if (currentList[position].priceChangePercentage24h!! >= 0) {
            holder.binding.textView2.setTextColor(ContextCompat.getColor(holder.binding.textView2.context, R.color.green))
            holder.binding.imageView3.setImageResource(R.drawable.arrowup)
            holder.binding.imageView3.setColorFilter(ContextCompat.getColor(holder.binding.imageView3.context, R.color.green))

        } else {
            holder.binding.textView2.setTextColor(ContextCompat.getColor(holder.binding.textView2.context, R.color.red))
            holder.binding.imageView3.setImageResource(R.drawable.arrowdown)
            holder.binding.imageView3.setColorFilter(ContextCompat.getColor(holder.binding.imageView3.context, R.color.red))
        }
        holder.binding.root.setOnClickListener {
            onCoinClick(currentList[position])
        }
        Glide.with(holder.binding.imageView).load(currentList[position].image).into(holder.binding.imageView)

        holder.binding.remove.setOnClickListener {
            viewModel.deleteCoin(currentList[position])
        }
    }
}