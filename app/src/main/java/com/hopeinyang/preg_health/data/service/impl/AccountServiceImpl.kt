package com.hopeinyang.preg_health.data.service.impl



import androidx.core.os.trace
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.hopeinyang.preg_health.LOGIN_SCREEN
import com.hopeinyang.preg_health.data.dto.User
import com.hopeinyang.preg_health.data.service.AccountService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
): AccountService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null


    override val currentUser: Flow<User>
        get() =  callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String): String {
        val user = auth.signInWithEmailAndPassword(email, password).await().user
        return user?.uid.orEmpty()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).await()
    }

    override suspend fun linkAccount(email: String, password: String) :Unit =
        trace(LINK_ACCOUNT_TRACE) {
            val credential = EmailAuthProvider.getCredential(email, password)
            auth.currentUser!!.linkWithCredential(credential).await()
        }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut(restartApp:(String)->Unit) {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
            restartApp(LOGIN_SCREEN)
        }
        auth.signOut()
        restartApp(LOGIN_SCREEN)

        // Sign the user back in anonymously.
        //createAnonymousAccount()
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}