package com.yuechang.ktv.service.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * K歌播放器
 * 支持原唱/伴奏切换、歌词同步
 */
@Singleton
class KaraokePlayer @Inject constructor(
    private val context: Context
) {
    private var player: ExoPlayer? = null
    
    private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.Idle)
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()
    
    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()
    
    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()
    
    private var accompanyPlayer: ExoPlayer? = null  // 伴奏播放器
    private var vocalPlayer: ExoPlayer? = null      // 原唱播放器
    
    private var isAccompanyMode = true  // 默认伴奏模式
    
    fun initialize() {
        player = ExoPlayer.Builder(context).build().apply {
            addListener(PlayerEventListener())
        }
    }
    
    /**
     * 加载歌曲
     * @param audioUrl 音频URL
     * @param accompanyUrl 伴奏URL（如果有）
     */
    fun loadSong(audioUrl: String, accompanyUrl: String? = null) {
        val mediaItem = MediaItem.fromUrl(audioUrl)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        
        // 如果有伴奏，创建伴奏播放器
        accompanyUrl?.let {
            accompanyPlayer = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUrl(it))
                prepare()
            }
        }
        
        _playbackState.value = PlaybackState.Loading
    }
    
    /**
     * 播放
     */
    fun play() {
        player?.play()
        accompanyPlayer?.play()
        _playbackState.value = PlaybackState.Playing
    }
    
    /**
     * 暂停
     */
    fun pause() {
        player?.pause()
        accompanyPlayer?.pause()
        _playbackState.value = PlaybackState.Paused
    }
    
    /**
     * 停止
     */
    fun stop() {
        player?.stop()
        accompanyPlayer?.stop()
        _playbackState.value = PlaybackState.Stopped
    }
    
    /**
     * 跳转到指定位置
     */
    fun seekTo(positionMs: Long) {
        player?.seekTo(positionMs)
        accompanyPlayer?.seekTo(positionMs)
    }
    
    /**
     * 切换原唱/伴奏模式
     */
    fun toggleAccompanyMode() {
        isAccompanyMode = !isAccompanyMode
        // 原唱模式下降低伴奏音量或静音
        accompanyPlayer?.volume = if (isAccompanyMode) 1f else 0f
    }
    
    /**
     * 设置音量 (0.0 - 1.0)
     */
    fun setVolume(volume: Float) {
        player?.volume = volume
    }
    
    /**
     * 释放资源
     */
    fun release() {
        player?.release()
        accompanyPlayer?.release()
        vocalPlayer?.release()
        player = null
        accompanyPlayer = null
        vocalPlayer = null
    }
    
    private inner class PlayerEventListener : android.media.session.MediaController.Callback() {
        // 实现播放事件回调
    }
}

/**
 * 播放状态
 */
sealed class PlaybackState {
    object Idle : PlaybackState()
    object Loading : PlaybackState()
    object Playing : PlaybackState()
    object Paused : PlaybackState()
    object Stopped : PlaybackState()
    object Completed : PlaybackState()
    data class Error(val message: String) : PlaybackState()
}