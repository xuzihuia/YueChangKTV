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

class SearchFragment : Fragment() {
    
    private lateinit var etSearch: EditText
    private lateinit var rvResults: RecyclerView
    private lateinit var tvHistory: TextView
    private lateinit var rvHistory: RecyclerView
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        etSearch = view.findViewById(R.id.et_search)
        rvResults = view.findViewById(R.id.rv_results)
        tvHistory = view.findViewById(R.id.tv_history)
        rvHistory = view.findViewById(R.id.rv_history)
        
        setupViews()
        loadHistory()
    }
    
    private fun setupViews() {
        rvResults.layoutManager = LinearLayoutManager(context)
        rvHistory.layoutManager = LinearLayoutManager(context)
        
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun loadHistory() {
        // Mock 搜索历史
        val history = listOf("周杰伦", "陈奕迅", "邓紫棋")
        rvHistory.adapter = HistoryAdapter(history)
    }
    
    private fun search(keyword: String) {
        if (keyword.isEmpty()) {
            rvResults.visibility = View.GONE
            tvHistory.visibility = View.VISIBLE
            rvHistory.visibility = View.VISIBLE
            return
        }
        
        rvResults.visibility = View.VISIBLE
        tvHistory.visibility = View.GONE
        rvHistory.visibility = View.GONE
        
        // Mock 搜索结果
        val results = listOf(
            Song("1", "${keyword}歌曲1", "歌手1"),
            Song("2", "${keyword}歌曲2", "歌手2")
        )
        rvResults.adapter = ResultAdapter(results)
    }
    
    inner class HistoryAdapter(
        private val history: List<String>
    ) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val text: TextView = view.findViewById(R.id.tv_text)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_text, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = history[position]
        }
        
        override fun getItemCount() = history.size
    }
    
    inner class ResultAdapter(
        private val songs: List<Song>
    ) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
        
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
            holder.name.text = songs[position].name
            holder.artist.text = songs[position].artist
        }
        
        override fun getItemCount() = songs.size
    }
}