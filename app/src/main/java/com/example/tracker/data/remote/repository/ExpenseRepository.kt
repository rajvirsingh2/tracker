package com.example.tracker.data.remote.repository

import com.example.tracker.data.model.Expenses
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val currUser=auth.currentUser
    suspend fun addExpense(expenses: Expenses){
        currUser?.let {
            firestore.collection("users").document(it.uid).collection("expenses").add(expenses).await()
        }
    }
    fun getExpenses()=firestore.collection("users").document(currUser!!.uid).collection("expenses")
        .orderBy("date", Query.Direction.DESCENDING).snapshots()
}