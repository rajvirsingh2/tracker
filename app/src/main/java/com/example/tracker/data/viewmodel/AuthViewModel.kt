package com.example.tracker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.data.model.User
import com.example.tracker.data.remote.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.tracker.core.util.Result

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    val userState=authRepository.userState
    private val _authState=MutableStateFlow<Result<User>?>(null)
    val authState: StateFlow<Result<User>?> = _authState

    fun login(email:String, password:String){
        viewModelScope.launch {
            _authState.value= Result.Loading
            _authState.value=authRepository.login(email,password)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Result.Loading
            _authState.value = authRepository.register(email, password)
        }
    }
}