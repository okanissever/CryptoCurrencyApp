package com.example.cryptoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCryptoBinding
import com.example.cryptoapp.model.CryptoItem

class CryptoAdapter : androidx.recyclerview.widget.ListAdapter<CryptoItem, CryptoAdapter.CryptoHolder>(NoteDiffCallback) {

    var onCoinClick:(CryptoItem) -> Unit = {}

    object NoteDiffCallback : DiffUtil.ItemCallback<CryptoItem>() {
        override fun areItemsTheSame(oldItem: CryptoItem, newItem: CryptoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoItem, newItem: CryptoItem): Boolean {
            return oldItem == newItem
        }
    }

    class CryptoHolder(val binding : ItemCryptoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.binding.tvName.text = currentList[position].name
        holder.binding.tvCurrentPrice.text = "${currentList[position].currentPrice.toString()}$ "
        holder.binding.textView2.text = String.format("%%%.2f", currentList[position].priceChangePercentage24h)

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


    }
}