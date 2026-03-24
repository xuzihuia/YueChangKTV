package com.yuechang.ktv.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.yuechang.ktv.databinding.ActivitySongDetailBinding
import com.yuechang.ktv.ui.viewmodel.SongDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 歌曲详情页
 */
@AndroidEntryPoint
class SongDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySongDetailBinding
    private val viewModel: SongDetailViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val songId = intent.getStringExtra("songId") ?: return
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnSingNow.setOnClickListener {
            // 跳转到 K歌界面
        }
        
        binding.btnFavorite.setOnClickListener {
            // 收藏
        }
        
        binding.btnShare.setOnClickListener {
            // 分享
        }
    }
}