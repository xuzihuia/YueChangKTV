package com.yuechang.ktv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuechang.ktv.domain.model.User
import com.yuechang.ktv.domain.usecase.GetCurrentUserUseCase
import com.yuechang.ktv.domain.usecase.LoginUseCase
import com.yuechang.ktv.domain.usecase.LogoutUseCase
import com.yuechang.ktv.domain.usecase.RefreshUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val refreshUserUseCase: RefreshUserUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    val isLoggedIn: StateFlow<Boolean> = _currentUser.map { it != null }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)
    
    fun login(phone: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            loginUseCase(phone, password)
                .onSuccess { user ->
                    _currentUser.value = user
                    _uiState.value = UserUiState.Success
                }
                .onFailure {
                    _uiState.value = UserUiState.Error(it.message ?: "登录失败")
                }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
                .onSuccess {
                    _currentUser.value = null
                    _uiState.value = UserUiState.NotLoggedIn
                }
        }
    }
    
    fun refreshUser() {
        viewModelScope.launch {
            refreshUserUseCase()
                .onSuccess { user ->
                    _currentUser.value = user
                }
        }
    }
}

sealed class UserUiState {
    object Loading : UserUiState()
    object NotLoggedIn : UserUiState()
    object Success : UserUiState()
    data class Error(val message: String) : UserUiState()
}