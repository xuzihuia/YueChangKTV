package com.yuechang.ktv.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuechang.ktv.domain.model.Song
import com.yuechang.ktv.domain.model.LyricLine
import com.yuechang.ktv.service.player.KaraokePlayer
import com.yuechang.ktv.service.player.PlaybackState
import com.yuechang.ktv.service.recorder.KaraokeRecorder
import com.yuechang.ktv.service.recorder.RecordingState
import com.yuechang.ktv.service.audio.ScoreEngine
import com.yuechang.ktv.service.audio.LyricsParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KaraokeViewModel @Inject constructor(
    private val player: KaraokePlayer,
    private val recorder: KaraokeRecorder,
    private val scoreEngine: ScoreEngine,
    private val lyricsParser: LyricsParser,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val songId: String? = savedStateHandle["songId"]
    
    private val _song = MutableStateFlow<Song?>(null)
    val song: StateFlow<Song?> = _song.asStateFlow()
    
    private val _lyrics = MutableStateFlow<List<LyricLine>>(emptyList())
    val lyrics: StateFlow<List<LyricLine>> = _lyrics.asStateFlow()
    
    private val _currentLyricIndex = MutableStateFlow(-1)
    val currentLyricIndex: StateFlow<Int> = _currentLyricIndex.asStateFlow()
    
    val playbackState: StateFlow<PlaybackState> = player.playbackState
    val currentPosition: StateFlow<Long> = player.currentPosition
    val duration: StateFlow<Long> = player.duration
    val recordingState: StateFlow<RecordingState> = recorder.recordingState
    
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()
    
    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()
    
    private val _isAccompanyMode = MutableStateFlow(true)
    val isAccompanyMode: StateFlow<Boolean> = _isAccompanyMode.asStateFlow()
    
    fun loadSong(song: Song, lyricsContent: String? = null) {
        _song.value = song
        
        // 加载音频
        player.loadSong(song.audioUrl ?: return, song.accompanyUrl)
        
        // 解析歌词
        lyricsContent?.let {
            _lyrics.value = lyricsParser.parseLrc(it)
        }
    }
    
    fun play() {
        player.play()
    }
    
    fun pause() {
        player.pause()
    }
    
    fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }
    
    fun toggleAccompanyMode() {
        player.toggleAccompanyMode()
        _isAccompanyMode.value = !_isAccompanyMode.value
    }
    
    fun startRecording(outputPath: String) {
        if (recorder.startRecording(outputPath)) {
            _isRecording.value = true
        }
    }
    
    fun stopRecording() {
        recorder.stopRecording()
        _isRecording.value = false
        
        // 计算得分
        calculateScore()
    }
    
    private fun calculateScore() {
        // TODO: 获取用户演唱数据和标准数据进行评分
        // val result = scoreEngine.calculateScore(userPitches, standardPitches)
        // _score.value = result.score
    }
    
    fun updateCurrentLyric() {
        val current = currentPosition.value
        val index = lyricsParser.getCurrentLine(_lyrics.value, current)
        _currentLyricIndex.value = index
    }
    
    override fun onCleared() {
        super.onCleared()
        player.release()
        recorder.stopRecording()
    }
}