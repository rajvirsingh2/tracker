package com.example.tracker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.data.model.Expenses
import com.example.tracker.data.remote.repository.AuthRepository
import com.example.tracker.data.remote.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val expenses = expenseRepository.getExpenses().map { snapshot ->
        snapshot.toObjects(Expenses::class.java)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun logout(){
        authRepository.signOut()
    }
}