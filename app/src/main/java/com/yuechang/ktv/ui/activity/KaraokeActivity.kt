package com.yuechang.ktv.ui.activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yuechang.ktv.R
import com.yuechang.ktv.service.KaraokePlayer
import com.yuechang.ktv.service.ScoreCalculator

class KaraokeActivity : AppCompatActivity() {
    
    private lateinit var player: KaraokePlayer
    private lateinit var tvSongName: TextView
    private lateinit var tvArtist: TextView
    private lateinit var tvScore: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var btnPlay: ImageButton
    private lateinit var btnAccompany: ImageButton
    
    private var songId: String? = null
    private var songName: String? = null
    private var artist: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_karaoke)
        
        player = KaraokePlayer(this)
        
        // 获取传入的歌曲信息
        songId = intent.getStringExtra("songId")
        songName = intent.getStringExtra("songName")
        artist = intent.getStringExtra("artist")
        
        initViews()
        setupListeners()
        loadSong()
    }
    
    private fun initViews() {
        tvSongName = findViewById(R.id.tv_song_name)
        tvArtist = findViewById(R.id.tv_artist)
        tvScore = findViewById(R.id.tv_score)
        seekBar = findViewById(R.id.seek_bar)
        btnPlay = findViewById(R.id.btn_play)
        btnAccompany = findViewById(R.id.btn_accompany)
        
        songName?.let { tvSongName.text = it }
        artist?.let { tvArtist.text = it }
    }
    
    private fun setupListeners() {
        btnPlay.setOnClickListener {
            if (player.isPlaying()) {
                player.pause()
                btnPlay.setImageResource(android.R.drawable.ic_media_play)
            } else {
                player.resume()
                btnPlay.setImageResource(android.R.drawable.ic_media_pause)
            }
        }
        
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
        // TODO: 加载歌曲
    }
    
    private fun updateScore(score: Int) {
        tvScore.text = "${score}分\n${ScoreCalculator.getLevel(score)}"
    }
    
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}