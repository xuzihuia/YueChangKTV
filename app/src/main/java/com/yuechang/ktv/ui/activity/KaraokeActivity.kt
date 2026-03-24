package com.yuechang.ktv.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.yuechang.ktv.databinding.ActivityKaraokeBinding
import com.yuechang.ktv.ui.viewmodel.KaraokeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * K歌界面
 * 对标唱享K歌核心K歌功能
 */
@AndroidEntryPoint
class KaraokeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityKaraokeBinding
    private val viewModel: KaraokeViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKaraokeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupObservers() {
        viewModel.playbackState.observe(this) { state ->
            // 更新播放按钮状态
        }
        
        viewModel.currentLyricIndex.observe(this) { index ->
            // 更新歌词高亮
        }
        
        viewModel.score.observe(this) { score ->
            // 更新分数显示
        }
    }
    
    private fun setupClickListeners() {
        binding.btnPlay.setOnClickListener {
            if (viewModel.isRecording.value) {
                viewModel.pause()
            } else {
                viewModel.play()
            }
        }
        
        binding.btnRecord.setOnClickListener {
            if (viewModel.isRecording.value) {
                viewModel.stopRecording()
            } else {
                viewModel.startRecording(getOutputPath())
            }
        }
        
        binding.btnAccompany.setOnClickListener {
            viewModel.toggleAccompanyMode()
        }
    }
    
    private fun getOutputPath(): String {
        return cacheDir.resolve("recording_${System.currentTimeMillis()}.mp3").absolutePath
    }
}