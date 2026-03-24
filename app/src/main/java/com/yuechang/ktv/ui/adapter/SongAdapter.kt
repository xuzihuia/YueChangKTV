package com.yuechang.ktv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuechang.ktv.databinding.ItemSongBinding
import com.yuechang.ktv.domain.model.Song

class SongAdapter(
    private val onItemClick: (Song) -> Unit
) : ListAdapter<Song, SongAdapter.ViewHolder>(SongDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(
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
        private val binding: ItemSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(song: Song, onItemClick: (Song) -> Unit) {
            binding.apply {
                tvSongName.text = song.name
                tvArtist.text = song.artist
                tvDuration.text = song.durationFormatted
                
                // VIP 标识
                tvVip.visibility = if (song.vipRequired) android.view.View.VISIBLE else android.view.View.GONE
                
                // 封面加载 (使用 Coil)
                // Coil.load(root.context, song.coverUrl) {
                //     crossfade(true)
                //     placeholder(R.drawable.ic_song_placeholder)
                //     into(ivCover)
                // }
                
                root.setOnClickListener { onItemClick(song) }
            }
        }
    }
    
    class SongDiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.songId == newItem.songId
        }
        
        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }
}