package com.example.tracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

@Entity
data class Expenses(
    @PrimaryKey(autoGenerate = true) val id: Long=0,
    @DocumentId val docId: String="",
    val title: String="",
    val amount: Double=0.0,
    val category: String="",
    val date: Long=System.currentTimeMillis()
)