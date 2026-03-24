package com.yuechang.ktv.service

import android.content.Context
import android.media.MediaPlayer
import java.io.File

class KaraokePlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    
    fun play(url: String) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepare()
            start()
        }
        isPlaying = true
    }
    
    fun playLocal(path: String) {
        val file = File(path)
        if (file.exists()) {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(path)
                prepare()
                start()
            }
            isPlaying = true
        }
    }
    
    fun pause() {
        mediaPlayer?.pause()
        isPlaying = false
    }
    
    fun resume() {
        mediaPlayer?.start()
        isPlaying = true
    }
    
    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
    }
    
    fun seekTo(ms: Int) {
        mediaPlayer?.seekTo(ms)
    }
    
    fun getCurrentPosition(): Int = mediaPlayer?.currentPosition ?: 0
    
    fun getDuration(): Int = mediaPlayer?.duration ?: 0
    
    fun isPlaying(): Boolean = isPlaying
    
    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}