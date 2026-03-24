package com.yuechang.ktv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuechang.ktv.domain.model.Song
import com.yuechang.ktv.domain.usecase.GetHotSongsUseCase
import com.yuechang.ktv.domain.usecase.GetNewSongsUseCase
import com.yuechang.ktv.domain.usecase.GetFreeSongsUseCase
import com.yuechang.ktv.domain.usecase.RefreshSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHotSongsUseCase: GetHotSongsUseCase,
    private val getNewSongsUseCase: GetNewSongsUseCase,
    private val getFreeSongsUseCase: GetFreeSongsUseCase,
    private val refreshSongsUseCase: RefreshSongsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    val hotSongs: StateFlow<List<Song>> = getHotSongsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val newSongs: StateFlow<List<Song>> = getNewSongsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val freeSongs: StateFlow<List<Song>> = getFreeSongsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    init {
        loadData()
    }
    
    fun loadData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            refreshSongsUseCase()
                .onSuccess {
                    _uiState.value = HomeUiState.Success
                }
                .onFailure {
                    _uiState.value = HomeUiState.Error(it.message ?: "加载失败")
                }
        }
    }
    
    fun refresh() {
        loadData()
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    object Success : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}