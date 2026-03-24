package com.yuechang.ktv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuechang.ktv.domain.usecase.SearchSongsUseCase
import com.yuechang.ktv.domain.model.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchSongsUseCase: SearchSongsUseCase
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<Song>>(emptyList())
    val searchResults: StateFlow<List<Song>> = _searchResults.asStateFlow()
    
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()
    
    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()
    
    fun updateQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun search() {
        val query = _searchQuery.value.trim()
        if (query.isEmpty()) return
        
        viewModelScope.launch {
            _isSearching.value = true
            
            // 添加到搜索历史
            addToHistory(query)
            
            searchSongsUseCase(query)
                .collect { results ->
                    _searchResults.value = results
                    _isSearching.value = false
                }
        }
    }
    
    fun searchWithQuery(query: String) {
        _searchQuery.value = query
        search()
    }
    
    private fun addToHistory(query: String) {
        val history = _searchHistory.value.toMutableList()
        // 移除重复项
        history.remove(query)
        // 添加到最前面
        history.add(0, query)
        // 最多保留 10 条
        if (history.size > 10) {
            history.removeLast()
        }
        _searchHistory.value = history
    }
    
    fun clearHistory() {
        _searchHistory.value = emptyList()
    }
    
    fun clearResults() {
        _searchResults.value = emptyList()
        _searchQuery.value = ""
    }
}