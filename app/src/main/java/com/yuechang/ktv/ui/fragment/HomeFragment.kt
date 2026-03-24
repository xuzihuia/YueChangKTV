package com.yuechang.ktv.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuechang.ktv.R
import com.yuechang.ktv.data.model.Song
import com.yuechang.ktv.ui.activity.KaraokeActivity

class HomeFragment : Fragment() {
    
    private lateinit var rvHotSongs: RecyclerView
    private lateinit var rvNewSongs: RecyclerView
    private lateinit var rvFreeSongs: RecyclerView
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        rvHotSongs = view.findViewById(R.id.rv_hot_songs)
        rvNewSongs = view.findViewById(R.id.rv_new_songs)
        rvFreeSongs = view.findViewById(R.id.rv_free_songs)
        
        setupRecyclerViews()
        loadData()
    }
    
    private fun setupRecyclerViews() {
        rvHotSongs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvNewSongs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvFreeSongs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
    
    private fun loadData() {
        // Mock 数据
        val hotSongs = getMockSongs()
        val newSongs = hotSongs.take(3)
        val freeSongs = hotSongs.filter { !it.isVip }
        
        rvHotSongs.adapter = SongAdapter(hotSongs) { song ->
            openKaraoke(song)
        }
        rvNewSongs.adapter = SongAdapter(newSongs) { song ->
            openKaraoke(song)
        }
        rvFreeSongs.adapter = SongAdapter(freeSongs) { song ->
            openKaraoke(song)
        }
    }
    
    private fun openKaraoke(song: Song) {
        val intent = Intent(requireContext(), KaraokeActivity::class.java).apply {
            putExtra("songId", song.id)
            putExtra("songName", song.name)
            putExtra("artist", song.artist)
        }
        startActivity(intent)
    }
    
    private fun getMockSongs(): List<Song> = listOf(
        Song("1", "起风了", "买辣椒也用券", "", 325000, false, 1500000),
        Song("2", "晴天", "周杰伦", "", 269000, false, 5000000),
        Song("3", "孤勇者", "陈奕迅", "", 256000, true, 8000000),
        Song("4", "稻香", "周杰伦", "", 223000, false, 3000000),
        Song("5", "光年之外", "邓紫棋", "", 235000, true, 6000000),
        Song("6", "海阔天空", "Beyond", "", 326000, false, 10000000)
    )
    
    inner class SongAdapter(
        private val songs: List<Song>,
        private val onItemClick: (Song) -> Unit
    ) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(R.id.tv_song_name)
            val artist: TextView = view.findViewById(R.id.tv_artist)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_song, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val song = songs[position]
            holder.name.text = song.name
            holder.artist.text = song.artist
            holder.itemView.setOnClickListener { onItemClick(song) }
        }
        
        override fun getItemCount() = songs.size
    }
}