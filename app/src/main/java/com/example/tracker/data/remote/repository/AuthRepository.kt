package com.example.tracker.data.remote.repository

import com.example.tracker.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import com.example.tracker.core.util.Result
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    private val _userState= MutableStateFlow<Result<User?>>(Result.Loading)
    val userState: StateFlow<Result<User?>> = _userState

    init{
        auth.addAuthStateListener {
            val firebaseUser=it.currentUser
            if(firebaseUser!=null){
                _userState.value=Result.Success(firebaseUser.toUser())
            }else{
                _userState.value=Result.Success(null)
            }
        }
    }

    fun getCurrUser():User?{
        return auth.currentUser?.toUser()
    }

    suspend fun register(email: String, password: String): Result<User>{
        return try{
            val res=auth.createUserWithEmailAndPassword(email,password).await()
            val firebaseUser = res.user
            if (firebaseUser != null) {
                Result.Success(firebaseUser.toUser())
            } else {
                Result.Error("User registration failed: user is null.")
            }
        }catch (e: Exception){
            Result.Error(e.localizedMessage ?: "An unknown error occurred during registration.")
        }
    }

    suspend fun login(email: String, password: String): Result<User>{
        return try {
            val res = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = res.user
            if (firebaseUser != null) {
                Result.Success(firebaseUser.toUser())
            } else {
                Result.Error("Login failed: user is null.")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unknown error occurred during login.")
        }
    }

    fun signOut() {
        auth.signOut()
    }

    private fun FirebaseUser.toUser(): User {
        return User(
            uid = this.uid,
            email = this.email
        )
    }
}