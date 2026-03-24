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
import androidx.viewpager2.widget.ViewPager2
import com.yuechang.ktv.R
import com.yuechang.ktv.data.model.Banner
import com.yuechang.ktv.data.model.Song
import com.yuechang.ktv.ui.activity.KaraokeActivity

/**
 * 首页Fragment
 * Banner轮播、热门推荐、分类入口
 */
class HomeFragment : Fragment() {
    
    private lateinit var viewPager: ViewPager2
    private lateinit var rvHotSongs: RecyclerView
    private lateinit var rvNewSongs: RecyclerView
    private lateinit var rvFreeSongs: RecyclerView
    
    private val banners = mutableListOf<Banner>()
    private val hotSongs = mutableListOf<Song>()
    private val newSongs = mutableListOf<Song>()
    private val freeSongs = mutableListOf<Song>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        loadData()
    }
    
    private fun initViews(view: View) {
        viewPager = view.findViewById(R.id.viewPager_banner)
        rvHotSongs = view.findViewById(R.id.rv_hot_songs)
        rvNewSongs = view.findViewById(R.id.rv_new_songs)
        rvFreeSongs = view.findViewById(R.id.rv_free_songs)
        
        // 设置横向滚动
        rvHotSongs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvNewSongs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvFreeSongs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
    
    private fun loadData() {
        // Mock 数据
        loadMockData()
        
        // 设置适配器
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
    
    private fun loadMockData() {
        // 热门歌曲
        hotSongs.addAll(listOf(
            Song("1", "起风了", "买辣椒也用券", coverUrl = "", playCount = 1500000),
            Song("2", "晴天", "周杰伦", coverUrl = "", playCount = 5000000),
            Song("3", "孤勇者", "陈奕迅", coverUrl = "", isVip = true, playCount = 8000000),
            Song("4", "稻香", "周杰伦", coverUrl = "", playCount = 3000000),
            Song("5", "光年之外", "邓紫棋", coverUrl = "", isVip = true, playCount = 6000000),
            Song("6", "海阔天空", "Beyond", coverUrl = "", playCount = 10000000)
        ))
        
        // 新歌
        newSongs.addAll(hotSongs.take(3))
        
        // 免费歌曲
        freeSongs.addAll(hotSongs.filter { !it.isVip })
    }
    
    private fun openKaraoke(song: Song) {
        val intent = Intent(requireContext(), KaraokeActivity::class.java).apply {
            putExtra("songId", song.id)
            putExtra("songName", song.name)
            putExtra("artist", song.artist)
        }
        startActivity(intent)
    }
    
    /**
     * 歌曲适配器
     */
    inner class SongAdapter(
        private val songs: List<Song>,
        private val onItemClick: (Song) -> Unit
    ) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tv_song_name)
            val tvArtist: TextView = view.findViewById(R.id.tv_artist)
            val tvPlayCount: TextView = view.findViewById(R.id.tv_play_count)
            val tvVip: TextView = view.findViewById(R.id.tv_vip_tag)
            
            init {
                view.setOnClickListener {
                    onItemClick(songs[adapterPosition])
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_song, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val song = songs[position]
            holder.tvName.text = song.name
            holder.tvArtist.text = song.artist
            holder.tvPlayCount.text = formatPlayCount(song.playCount)
            holder.tvVip.visibility = if (song.isVip) View.VISIBLE else View.GONE
        }
        
        override fun getItemCount() = songs.size
        
        private fun formatPlayCount(count: Long): String {
            return when {
                count >= 10000000 -> "${count / 10000000}千万"
                count >= 10000 -> "${count / 10000}万"
                else -> count.toString()
            }
        }
    }
}