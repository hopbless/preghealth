package com.hopeinyang.preg_health.ui.login

import androidx.compose.runtime.mutableStateOf
import com.hopeinyang.preg_health.*
import com.hopeinyang.preg_health.common.ext.isValidEmail
import com.hopeinyang.preg_health.common.snackbar.SnackbarManager
import com.hopeinyang.preg_health.data.dto.UserInfo
import com.hopeinyang.preg_health.data.service.AccountService
import com.hopeinyang.preg_health.data.service.LogService
import com.hopeinyang.preg_health.data.service.StorageService
import com.hopeinyang.preg_health.navigation.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    logService: LogService
) : MainViewModel(logService){

    var  uiState = mutableStateOf(LoginUiState())
        private set



    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password



    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)

    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }



    fun onSignInClick(openAndPopUp: (String, String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_password_error)
            return
        }

        launchCatching {
            uiState.value = uiState.value.copy(isLoginInProgress = true)

            val userId = accountService.authenticate(email, password)

            storageService.getUserInfo(userId, openAndPopUp, :: userAccount)


        }
    }

    private fun userAccount(successful: Boolean,
                            userInfo: UserInfo?,
                            openAndPopUp: (String, String, String) -> Unit
    ){

        launchCatching(snackbar = true){
            if (successful){
                val isDoctorAccount: Boolean? = userInfo?.doctorAccount

                if (isDoctorAccount != null && isDoctorAccount){
                    openAndPopUp(DOCTOR_HOME_SCREEN, LOGIN_SCREEN, userInfo.userId!!)

                }else if (isDoctorAccount != null){
                    openAndPopUp(PATIENT_DATA_NAV, LOGIN_SCREEN, userInfo.userId!!)

                }else{

                    openAndPopUp(SIGN_UP_SCREEN, LOGIN_SCREEN, " ")
                }
            }else{
                uiState.value = uiState.value.copy(isLoginInProgress = false)
                SnackbarManager.showMessage(R.string.login_error)
            }

        }



    }

    fun onRegisterClick(register: String, openAndPopUp: (String, String, String) -> Unit){
        launchCatching {
            openAndPopUp(SIGN_UP_SCREEN, LOGIN_SCREEN, " ")
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(R.string.recovery_email_sent)
        }
    }

}