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
import com.yuechang.ktv.data.mock.MockData

class HomeFragment : Fragment() {
    
    private lateinit var recyclerView: RecyclerView
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        recyclerView = view.findViewById(R.id.rv_hot_songs)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        
        val songs = MockData.getMockSongs()
        recyclerView.adapter = SimpleSongAdapter(songs.take(5)) { song ->
            // 点击事件
        }
    }
}

class SimpleSongAdapter(
    private val songs: List<com.yuechang.ktv.data.local.entity.SongEntity>,
    private val onItemClick: (com.yuechang.ktv.data.local.entity.SongEntity) -> Unit
) : RecyclerView.Adapter<SimpleSongAdapter.ViewHolder>() {
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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