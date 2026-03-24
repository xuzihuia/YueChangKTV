package com.yuechang.ktv.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yuechang.ktv.R
import com.yuechang.ktv.service.audio.ScoreEngine
import com.yuechang.ktv.service.audio.ScoreResult
import com.yuechang.ktv.service.player.KaraokePlayer
import com.yuechang.ktv.service.recorder.AudioRecorder

/**
 * K歌页面
 * 视频播放、原唱/伴奏切换、录音、评分
 */
class KaraokeActivity : AppCompatActivity() {
    
    // Views
    private lateinit var tvSongName: TextView
    private lateinit var tvArtist: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvLevel: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var tvCurrentTime: TextView
    private lateinit var tvTotalTime: TextView
    private lateinit var btnPlay: ImageButton
    private lateinit var btnAccompany: ImageButton
    private lateinit var btnRecord: ImageButton
    private lateinit var lyricsView: TextView
    
    // Services
    private lateinit var player: KaraokePlayer
    private lateinit var recorder: AudioRecorder
    private val scoreEngine = ScoreEngine()
    
    // Data
    private var songId: String? = null
    private var songName: String? = null
    private var artist: String? = null
    
    // State
    private var isRecording = false
    private var currentScore = 0
    private val updateHandler = Handler(Looper.getMainLooper())
    
    private val updateRunnable = object : Runnable {
        override fun run() {
            if (player.isPlaying()) {
                val current = player.getCurrentPosition()
                val duration = player.getDuration()
                
                seekBar.progress = current
                seekBar.max = duration
                tvCurrentTime.text = formatTime(current)
                tvTotalTime.text = formatTime(duration)
                
                // 更新歌词
                updateLyrics(current)
                
                updateHandler.postDelayed(this, 100)
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_karaoke)
        
        // 获取歌曲信息
        songId = intent.getStringExtra("songId")
        songName = intent.getStringExtra("songName")
        artist = intent.getStringExtra("artist")
        
        initViews()
        initServices()
        setupListeners()
        loadSong()
    }
    
    private fun initViews() {
        tvSongName = findViewById(R.id.tv_song_name)
        tvArtist = findViewById(R.id.tv_artist)
        tvScore = findViewById(R.id.tv_score)
        tvLevel = findViewById(R.id.tv_level)
        seekBar = findViewById(R.id.seek_bar)
        tvCurrentTime = findViewById(R.id.tv_current_time)
        tvTotalTime = findViewById(R.id.tv_total_time)
        btnPlay = findViewById(R.id.btn_play)
        btnAccompany = findViewById(R.id.btn_accompany)
        btnRecord = findViewById(R.id.btn_record)
        lyricsView = findViewById(R.id.tv_lyrics)
        
        // 显示歌曲信息
        tvSongName.text = songName
        tvArtist.text = artist
    }
    
    private fun initServices() {
        player = KaraokePlayer(this)
        recorder = AudioRecorder()
        
        player.onCompletion = {
            stopRecording()
            updateHandler.removeCallbacks(updateRunnable)
            showFinalScore()
        }
        
        player.onError = { error ->
            tvScore.text = "播放错误"
        }
    }
    
    private fun setupListeners() {
        // 播放/暂停
        btnPlay.setOnClickListener {
            if (player.isPlaying()) {
                player.pause()
                btnPlay.setImageResource(android.R.drawable.ic_media_play)
            } else {
                player.play()
                btnPlay.setImageResource(android.R.drawable.ic_media_pause)
                updateHandler.post(updateRunnable)
            }
        }
        
        // 原唱/伴奏切换
        btnAccompany.setOnClickListener {
            player.toggleAccompany()
            btnAccompany.setImageResource(
                if (player.isAccompanyMode()) {
                    android.R.drawable.ic_menu_manage
                } else {
                    android.R.drawable.ic_menu_gallery
                }
            )
        }
        
        // 录音
        btnRecord.setOnClickListener {
            if (isRecording) {
                stopRecording()
            } else {
                startRecording()
            }
        }
        
        // 进度条
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    
    private fun loadSong() {
        // TODO: 从API加载歌曲URL
        // 模拟加载
        tvScore.text = "0"
        tvLevel.text = ""
    }
    
    private fun startRecording() {
        val outputPath = cacheDir.absolutePath + "/recording_${System.currentTimeMillis()}.pcm"
        if (recorder.startRecording(outputPath)) {
            isRecording = true
            btnRecord.setImageResource(android.R.drawable.ic_btn_speak_now)
            btnRecord.setBackgroundColor(0xFFFF0000.toInt())
        }
    }
    
    private fun stopRecording() {
        if (isRecording) {
            recorder.stopRecording()
            isRecording = false
            btnRecord.setImageResource(android.R.drawable.ic_btn_speak_now)
            btnRecord.setBackgroundColor(0xFF666666.toInt())
        }
    }
    
    private fun updateLyrics(currentTime: Int) {
        // TODO: 根据当前时间更新歌词显示
    }
    
    private fun updateScore(pitchScore: Double) {
        // 实时更新得分
        currentScore = ((currentScore * 0.8) + (pitchScore * 0.2)).toInt()
        tvScore.text = currentScore.toString()
        tvLevel.text = scoreEngine.getLevel(currentScore)
    }
    
    private fun showFinalScore() {
        val level = scoreEngine.getLevel(currentScore)
        tvScore.text = currentScore.toString()
        tvLevel.text = level
    }
    
    private fun formatTime(ms: Int): String {
        val seconds = ms / 1000
        val min = seconds / 60
        val sec = seconds % 60
        return String.format("%02d:%02d", min, sec)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        updateHandler.removeCallbacks(updateRunnable)
        player.release()
        recorder.release()
    }
}