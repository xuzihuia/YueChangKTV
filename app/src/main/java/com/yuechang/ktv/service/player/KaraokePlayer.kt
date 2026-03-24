package com.yuechang.ktv.service.player

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import java.io.IOException

/**
 * K歌播放器
 * 支持原唱/伴奏切换、播放控制
 */
class KaraokePlayer(private val context: Context) {
    
    private var mainPlayer: MediaPlayer? = null       // 主播放器（原唱/视频）
    private var accompanyPlayer: MediaPlayer? = null  // 伴奏播放器
    
    private var isAccompanyMode = false  // 是否伴奏模式
    private var isPlaying = false
    
    private var currentUrl: String? = null
    private var accompanyUrl: String? = null
    
    var onPositionChanged: ((Int) -> Unit)? = null
    var onCompletion: (() -> Unit)? = null
    var onError: ((String) -> Unit)? = null
    
    /**
     * 加载歌曲
     * @param mainUrl 主音轨URL（原唱或视频）
     * @param accompanyUrl 伴奏URL
     */
    fun load(mainUrl: String, accompanyUrl: String? = null) {
        this.currentUrl = mainUrl
        this.accompanyUrl = accompanyUrl
        
        release()
        
        try {
            // 创建主播放器
            mainPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(context, Uri.parse(mainUrl))
                setOnPreparedListener {
                    onPositionChanged?.invoke(0)
                }
                setOnCompletionListener {
                    isPlaying = false
                    onCompletion?.invoke()
                }
                setOnErrorListener { _, what, extra ->
                    onError?.invoke("播放错误: $what, $extra")
                    true
                }
                prepareAsync()
            }
            
            // 如果有伴奏，创建伴奏播放器
            if (!accompanyUrl.isNullOrEmpty()) {
                accompanyPlayer = MediaPlayer().apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(context, Uri.parse(accompanyUrl))
                    prepareAsync()
                }
            }
            
        } catch (e: IOException) {
            onError?.invoke("加载失败: ${e.message}")
        }
    }
    
    /**
     * 播放
     */
    fun play() {
        mainPlayer?.start()
        accompanyPlayer?.start()
        isPlaying = true
    }
    
    /**
     * 暂停
     */
    fun pause() {
        mainPlayer?.pause()
        accompanyPlayer?.pause()
        isPlaying = false
    }
    
    /**
     * 停止
     */
    fun stop() {
        mainPlayer?.stop()
        accompanyPlayer?.stop()
        isPlaying = false
    }
    
    /**
     * 跳转到指定位置
     * @param positionMs 毫秒
     */
    fun seekTo(positionMs: Int) {
        mainPlayer?.seekTo(positionMs)
        accompanyPlayer?.seekTo(positionMs)
    }
    
    /**
     * 切换原唱/伴奏模式
     */
    fun toggleAccompany() {
        if (accompanyPlayer == null) return
        
        isAccompanyMode = !isAccompanyMode
        
        if (isAccompanyMode) {
            // 伴奏模式：原唱静音，伴奏正常
            mainPlayer?.setVolume(0f, 0f)
            accompanyPlayer?.setVolume(1f, 1f)
        } else {
            // 原唱模式：原唱正常，伴奏静音
            mainPlayer?.setVolume(1f, 1f)
            accompanyPlayer?.setVolume(0f, 0f)
        }
    }
    
    /**
     * 设置音量
     * @param volume 0.0 - 1.0
     */
    fun setVolume(volume: Float) {
        val v = volume.coerceIn(0f, 1f)
        if (isAccompanyMode) {
            accompanyPlayer?.setVolume(v, v)
        } else {
            mainPlayer?.setVolume(v, v)
        }
    }
    
    /**
     * 获取当前位置
     */
    fun getCurrentPosition(): Int = mainPlayer?.currentPosition ?: 0
    
    /**
     * 获取总时长
     */
    fun getDuration(): Int = mainPlayer?.duration ?: 0
    
    /**
     * 是否正在播放
     */
    fun isPlaying(): Boolean = isPlaying
    
    /**
     * 是否伴奏模式
     */
    fun isAccompanyMode(): Boolean = isAccompanyMode
    
    /**
     * 释放资源
     */
    fun release() {
        mainPlayer?.release()
        mainPlayer = null
        accompanyPlayer?.release()
        accompanyPlayer = null
        isPlaying = false
    }
}