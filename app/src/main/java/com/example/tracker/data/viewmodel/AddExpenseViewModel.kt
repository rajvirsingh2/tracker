package com.example.tracker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker.data.model.Expenses
import com.example.tracker.data.remote.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
): ViewModel() {
    fun addExpense(title:String, amount:Double, category:String, date:Long){
        viewModelScope.launch {
            val expense= Expenses(
                title = title,
                amount = amount,
                category = category,
                date = date
            )
            expenseRepository.addExpense(expense)
        }
    }
}