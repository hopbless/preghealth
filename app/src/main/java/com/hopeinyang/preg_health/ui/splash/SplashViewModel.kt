package com.hopeinyang.preg_health.ui.splash

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hopeinyang.preg_health.*
import com.hopeinyang.preg_health.data.dto.UserInfo
import com.hopeinyang.preg_health.data.service.AccountService
import com.hopeinyang.preg_health.data.service.LogService
import com.hopeinyang.preg_health.data.service.StorageService
import com.hopeinyang.preg_health.navigation.MainViewModel
import com.hopeinyang.preg_health.ui.homeScreen.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService


) : MainViewModel(logService){

    var uiState = mutableStateOf(SplashScreenUiState())
        private set

    init {
        viewModelScope.launch (Dispatchers.IO) {
            val userId = accountService.currentUserId

            if(userId.isNotEmpty()){
                val hasUser = accountService.hasUser
                val userInfo = storageService.getUserInfoFlow(userId).collect{
                    uiState.value = uiState.value.copy(
                        isDoctorAccount = it.data?.doctorAccount!!,
                        isUserRegistered = hasUser,
                        userId = userId
                    )
                }
            }


        }

    }


    fun onAppStart(
        openAndPopUp: (String, String, String)  -> Unit,
        navigateToLogin: (String, String) -> Unit
    ) {


            if (!uiState.value.isDoctorAccount && uiState.value.isUserRegistered){
                //Log.d("UserId: ", userId)

                openAndPopUp(PATIENT_DATA_NAV, SPLASH_SCREEN, uiState.value.userId)
            }
            else if(uiState.value.isDoctorAccount && uiState.value.isUserRegistered){

                openAndPopUp(DOCTOR_HOME_SCREEN, SPLASH_SCREEN, uiState.value.userId)

            }else{

                    navigateToLogin(LOGIN_SCREEN, SPLASH_SCREEN)
            }







    }



//    private fun createAccount(openAndPopUp: (String, String) -> Unit) {
//        launchCatching(snackbar = false) {
////            try {
////                accountService.createAnonymousAccount()
////            } catch (ex: FirebaseAuthException) {
////                showError.value = true
////                throw ex
////            }
//            openAndPopUp(SIGN_UP_SCREEN, SPLASH_SCREEN)
//        }
//    }

}


