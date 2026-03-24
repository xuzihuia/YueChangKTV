package com.yuechang.ktv.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yuechang.ktv.data.local.entity.SongEntity
import com.yuechang.ktv.data.mock.MockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SongDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val songId: String? = savedStateHandle["songId"]
    
    private val _song = MutableStateFlow<SongEntity?>(null)
    val song: StateFlow<SongEntity?> = _song
    
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite
    
    init {
        loadSong()
    }
    
    private fun loadSong() {
        songId?.let { id ->
            _song.value = MockData.getMockSongs().find { it.songId == id }
        }
    }
    
    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value
    }
}