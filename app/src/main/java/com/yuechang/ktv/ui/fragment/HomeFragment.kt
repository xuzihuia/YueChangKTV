package com.yuechang.ktv.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuechang.ktv.data.local.entity.SongEntity
import com.yuechang.ktv.data.mock.MockData
import com.yuechang.ktv.databinding.FragmentHomeBinding
import com.yuechang.ktv.ui.activity.KaraokeActivity
import com.yuechang.ktv.ui.adapter.SongAdapter
import com.yuechang.ktv.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 首页 Fragment
 * 对标唱享K歌首页：热门推荐、分类入口、Banner
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var hotSongAdapter: SongAdapter
    private lateinit var newSongAdapter: SongAdapter
    private lateinit var freeSongAdapter: SongAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupObservers()
        loadMockData()
    }
    
    private fun setupRecyclerViews() {
        val onSongClick: (SongEntity) -> Unit = { song ->
            openKaraokeActivity(song)
        }
        
        hotSongAdapter = SongAdapter(onSongClick)
        newSongAdapter = SongAdapter(onSongClick)
        freeSongAdapter = SongAdapter(onSongClick)
        
        binding.rvHotSongs.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = hotSongAdapter
        }
        
        binding.rvNewSongs.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newSongAdapter
        }
        
        binding.rvFreeSongs.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = freeSongAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeUiState.Loading -> {
                    // 显示加载中
                }
                is HomeUiState.Success -> {
                    // 加载成功
                }
                is HomeUiState.Error -> {
                    // 显示错误
                }
            }
        }
    }
    
    private fun loadMockData() {
        // 加载 Mock 数据
        val songs = MockData.getMockSongs()
        
        hotSongAdapter.submitList(songs.filter { it.tags.contains("热门") })
        newSongAdapter.submitList(songs.sortedByDescending { it.createdAt }.take(5))
        freeSongAdapter.submitList(songs.filter { !it.vipRequired })
    }
    
    private fun openKaraokeActivity(song: SongEntity) {
        val intent = Intent(requireContext(), KaraokeActivity::class.java).apply {
            putExtra("songId", song.songId)
            putExtra("songName", song.name)
            putExtra("artist", song.artist)
        }
        startActivity(intent)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// 简化版 SongAdapter，接受 Entity
class SongAdapter(
    private val onItemClick: (SongEntity) -> Unit
) : androidx.recyclerview.widget.ListAdapter<SongEntity, SongAdapter.ViewHolder>(DiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = com.yuechang.ktv.databinding.ItemSongBinding.inflate(
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
        private val binding: com.yuechang.ktv.databinding.ItemSongBinding
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        
        fun bind(song: SongEntity, onItemClick: (SongEntity) -> Unit) {
            binding.apply {
                tvSongName.text = song.name
                tvArtist.text = song.artist
                
                val minutes = song.duration / 60000
                val seconds = (song.duration % 60000) / 1000
                tvDuration.text = String.format("%02d:%02d", minutes, seconds)
                
                tvVip.visibility = if (song.vipRequired) View.VISIBLE else View.GONE
                
                root.setOnClickListener { onItemClick(song) }
            }
        }
    }
    
    class DiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<SongEntity>() {
        override fun areItemsTheSame(oldItem: SongEntity, newItem: SongEntity) = 
            oldItem.songId == newItem.songId
        override fun areContentsTheSame(oldItem: SongEntity, newItem: SongEntity) = 
            oldItem == newItem
    }
}