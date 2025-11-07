package com.example.tracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tracker.data.local.dao.ExpensesDao
import com.example.tracker.data.model.Expenses

@Database(entities = [Expenses::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun expensesDao(): ExpensesDao
}