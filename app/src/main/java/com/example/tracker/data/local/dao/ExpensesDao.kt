package com.example.tracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.tracker.data.model.Expenses
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertExpense(expenses: Expenses)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expenses>>

    @Query("DELETE FROM expenses")
    suspend fun deleteAllExpenses()
}