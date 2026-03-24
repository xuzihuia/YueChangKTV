package com.yuechang.ktv.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuechang.ktv.R
import com.yuechang.ktv.data.model.Song

/**
 * 搜索Fragment
 * 关键词搜索、搜索历史、热门搜索
 */
class SearchFragment : Fragment() {
    
    private lateinit var etSearch: EditText
    private lateinit var rvHistory: RecyclerView
    private lateinit var rvResults: RecyclerView
    private lateinit var layoutHistory: View
    private lateinit var tvClearHistory: TextView
    
    private val searchHistory = mutableListOf<String>()
    private val searchResults = mutableListOf<Song>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        setupListeners()
        loadHistory()
    }
    
    private fun initViews(view: View) {
        etSearch = view.findViewById(R.id.et_search)
        rvHistory = view.findViewById(R.id.rv_history)
        rvResults = view.findViewById(R.id.rv_results)
        layoutHistory = view.findViewById(R.id.layout_history)
        tvClearHistory = view.findViewById(R.id.tv_clear_history)
        
        rvHistory.layoutManager = LinearLayoutManager(context)
        rvResults.layoutManager = LinearLayoutManager(context)
    }
    
    private fun setupListeners() {
        // 搜索输入监听
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s?.toString()?.trim() ?: ""
                if (keyword.isNotEmpty()) {
                    search(keyword)
                } else {
                    showHistory()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        
        // 清除历史
        tvClearHistory.setOnClickListener {
            searchHistory.clear()
            rvHistory.adapter?.notifyDataSetChanged()
        }
    }
    
    private fun loadHistory() {
        // Mock 搜索历史
        searchHistory.addAll(listOf("周杰伦", "陈奕迅", "邓紫棋", "Beyond"))
        
        rvHistory.adapter = HistoryAdapter(searchHistory) { keyword ->
            etSearch.setText(keyword)
        }
    }
    
    private fun search(keyword: String) {
        layoutHistory.visibility = View.GONE
        rvResults.visibility = View.VISIBLE
        
        // Mock 搜索结果
        searchResults.clear()
        searchResults.addAll(listOf(
            Song("1", "${keyword}-歌曲1", "歌手1"),
            Song("2", "${keyword}-歌曲2", "歌手2"),
            Song("3", "${keyword}-歌曲3", "歌手3")
        ))
        
        rvResults.adapter = ResultAdapter(searchResults) { song ->
            // 点击歌曲
        }
    }
    
    private fun showHistory() {
        layoutHistory.visibility = View.VISIBLE
        rvResults.visibility = View.GONE
    }
    
    inner class HistoryAdapter(
        private val history: List<String>,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvKeyword: TextView = view.findViewById(R.id.tv_keyword)
            
            init {
                view.setOnClickListener { onItemClick(history[adapterPosition]) }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_history, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvKeyword.text = history[position]
        }
        
        override fun getItemCount() = history.size
    }
    
    inner class ResultAdapter(
        private val songs: List<Song>,
        private val onItemClick: (Song) -> Unit
    ) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tv_song_name)
            val tvArtist: TextView = view.findViewById(R.id.tv_artist)
            
            init {
                view.setOnClickListener { onItemClick(songs[adapterPosition]) }
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
        }
        
        override fun getItemCount() = songs.size
    }
}