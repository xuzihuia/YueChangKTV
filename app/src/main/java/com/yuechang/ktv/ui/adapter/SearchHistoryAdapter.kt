package com.yuechang.ktv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuechang.ktv.databinding.ItemSearchHistoryBinding

class SearchHistoryAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<String, SearchHistoryAdapter.ViewHolder>(HistoryDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
    
    class ViewHolder(
        private val binding: ItemSearchHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(query: String, onItemClick: (String) -> Unit) {
            binding.apply {
                tvQuery.text = query
                root.setOnClickListener { onItemClick(query) }
            }
        }
    }
    
    class HistoryDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
        
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}