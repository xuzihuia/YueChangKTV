package com.yuechang.ktv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.yuechang.ktv.R
import com.yuechang.ktv.data.mock.MockData

class HomeFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // 动态添加歌曲列表
        val songsContainer = view.findViewById<LinearLayout>(R.id.songs_container)
        val songs = MockData.getMockSongs()
        
        for (song in songs) {
            val songView = TextView(requireContext()).apply {
                text = "${song.name} - ${song.artist}"
                setTextColor(resources.getColor(R.color.text_primary, null))
                textSize = 16f
                setPadding(16, 16, 16, 16)
            }
            songsContainer?.addView(songView)
        }
    }
}