package com.yuechang.ktv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuechang.ktv.databinding.ItemCategoryBinding
import com.yuechang.ktv.data.local.entity.CategoryEntity

class CategoryAdapter(
    private val onItemClick: (CategoryEntity) -> Unit
) : ListAdapter<CategoryEntity, CategoryAdapter.ViewHolder>(CategoryDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(
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
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(category: CategoryEntity, onItemClick: (CategoryEntity) -> Unit) {
            binding.apply {
                tvCategoryName.text = category.name
                
                root.setOnClickListener { onItemClick(category) }
            }
        }
    }
    
    class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryEntity>() {
        override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
            return oldItem.categoryId == newItem.categoryId
        }
        
        override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
            return oldItem == newItem
        }
    }
}