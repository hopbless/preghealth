package com.hopeinyang.preg_health.data.service

import com.google.firebase.auth.UserInfo
import com.hopeinyang.preg_health.data.dto.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String): String
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut(restartApp:(String)->Unit)
}