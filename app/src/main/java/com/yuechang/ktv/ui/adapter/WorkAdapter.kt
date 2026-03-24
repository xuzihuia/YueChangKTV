package com.yuechang.ktv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuechang.ktv.databinding.ItemWorkBinding
import com.yuechang.ktv.data.local.entity.WorkEntity

class WorkAdapter(
    private val onItemClick: (WorkEntity) -> Unit
) : ListAdapter<WorkEntity, WorkAdapter.ViewHolder>(WorkDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkBinding.inflate(
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
        private val binding: ItemWorkBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(work: WorkEntity, onItemClick: (WorkEntity) -> Unit) {
            binding.apply {
                tvSongName.text = work.songName
                tvArtist.text = work.artist
                tvScore.text = "${work.score}分"
                tvPlayCount.text = "${work.playCount}次播放"
                
                root.setOnClickListener { onItemClick(work) }
            }
        }
    }
    
    class WorkDiffCallback : DiffUtil.ItemCallback<WorkEntity>() {
        override fun areItemsTheSame(oldItem: WorkEntity, newItem: WorkEntity): Boolean {
            return oldItem.workId == newItem.workId
        }
        
        override fun areContentsTheSame(oldItem: WorkEntity, newItem: WorkEntity): Boolean {
            return oldItem == newItem
        }
    }
}