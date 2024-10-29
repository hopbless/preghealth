package com.hopeinyang.preg_health.ui.signup

import androidx.compose.runtime.mutableStateOf
import com.hopeinyang.preg_health.*
import com.hopeinyang.preg_health.common.ext.*
import com.hopeinyang.preg_health.common.snackbar.SnackbarManager
import com.hopeinyang.preg_health.data.dao.PreHealthRepository
import com.hopeinyang.preg_health.data.dto.UserInfo
import com.hopeinyang.preg_health.data.service.AccountService
import com.hopeinyang.preg_health.data.service.LogService
import com.hopeinyang.preg_health.data.service.StorageService
import com.hopeinyang.preg_health.navigation.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    logService: LogService
) : MainViewModel(logService) {

    var signUpInProgress = mutableStateOf(false)

    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    private val phoneNumber
        get() = uiState.value.phoneNumber

    private val age
        get() = uiState.value.age

    private val weekNo
        get() = uiState.value.weekNo

    private val height
        get() = uiState.value.height

    private val firstName
        get() = uiState.value.firstName

    private val lastName
        get() = uiState.value.lastName
    private val address
        get() = uiState.value.address

    private val checkBoxValue
        get() = uiState.value.checkBoxValue

    private val doctorCode
        get() = uiState.value.doctorCode

    private val hospitalName
        get() = uiState.value.hospitalName

    private val isDoctorAccount
        get() = uiState.value.isDoctorAccount


    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onAddressChange(newValue: String) {
        uiState.value = uiState.value.copy(address = newValue)
    }

    fun onFirstNameChange(newValue: String) {
        uiState.value = uiState.value.copy(firstName = newValue)
    }

    fun onLastNameChange(newValue: String) {
        uiState.value = uiState.value.copy(lastName = newValue)
    }

    fun onAgeChange(newValue: String) {
        uiState.value = uiState.value.copy(age = newValue)
    }



    fun onPregnancyWeekChange(newValue: String) {
        uiState.value = uiState.value.copy(weekNo = newValue)
    }

    fun onPhoneNumberChange(newValue: String) {
        uiState.value = uiState.value.copy(phoneNumber = newValue)
    }

    fun onHeightChange(newValue: String) {
        uiState.value = uiState.value.copy(height = newValue)
    }

    fun onCheckBoxChange(newValue: Boolean) {
        uiState.value = uiState.value.copy(checkBoxValue = newValue )
    }

    fun onDoctorCodeChange(newValue: String) {
        uiState.value = uiState.value.copy(doctorCode = newValue )
    }

    fun onHospitalNameChange(newValue: String) {
        uiState.value = uiState.value.copy(hospitalName = newValue )
    }

    fun onDoctorAccountSelected(newValue: Boolean){
        uiState.value = uiState.value.copy(isDoctorAccount = newValue)
    }


    fun onSignUpClick(openAndPopUp: (String, String, String) -> Unit) {
        if (!uiState.value.isDoctorAccount){
            if (!email.isValidEmail()) {
                SnackbarManager.showMessage(R.string.email_error)
                return
            }

            if (!password.isValidPassword()) {
                SnackbarManager.showMessage(R.string.password_error)
                return
            }

            if (!password.passwordMatches(uiState.value.repeatPassword)) {
                SnackbarManager.showMessage(R.string.password_match_error)
                return
            }

            if (!age.isValidAge()) {
                SnackbarManager.showMessage(R.string.age_error)
                return
            }

            if (!weekNo.isValidWeekNo()) {
                SnackbarManager.showMessage(R.string.age_error)
                return
            }

            if (!phoneNumber.isValidPhoneNumber()) {
                SnackbarManager.showMessage(R.string.phone_number_error)
                return
            }

            if (!height.isValidHeight()) {
                SnackbarManager.showMessage(R.string.height_error)
                return
            }

            if (!firstName.isValidFirstName()) {
                SnackbarManager.showMessage(R.string.first_name_error)
                return
            }

            if (!lastName.isValidLastName()) {
                SnackbarManager.showMessage(R.string.last_name_error)
                return
            }

            if (!address.isValidAddress()) {
                SnackbarManager.showMessage(R.string.address_error)
                return
            }

            if(!checkBoxValue){
                SnackbarManager.showMessage(R.string.accept_terms)
                return
            }



            signUpInProgress.value = true
            launchCatching {
                accountService.createUserWithEmailAndPassword(email, password)
                val currentUserId = accountService.currentUserId

                val userInfo = UserInfo(
                    emailAddress = email,
                    firstName = firstName,
                    lastName = lastName,
                    height = height,
                    age = age,
                    pregnancyWeek = weekNo,
                    address = address,
                    phoneNumber = phoneNumber,
                    userId = currentUserId,
                    doctorSelected = false,
                    doctorAccount = isDoctorAccount,
                    doctorCode = "",
                    id = ""

                )
                storageService.saveUserInfo(userInfo)
                //preHealthRepository.insertUserInfo(userInfo)
                openAndPopUp(PATIENT_DATA_NAV, SIGN_UP_SCREEN, currentUserId)
            }
        }else{
            onDoctorSignUpClick(openAndPopUp)
        }
    }

    private fun onDoctorSignUpClick(openAndPopUp: (String, String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(R.string.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(R.string.password_match_error)
            return
        }


        if (!phoneNumber.isValidPhoneNumber()) {
            SnackbarManager.showMessage(R.string.phone_number_error)
            return
        }


        if (!firstName.isValidFirstName()) {
            SnackbarManager.showMessage(R.string.first_name_error)
            return
        }

        if (!lastName.isValidLastName()) {
            SnackbarManager.showMessage(R.string.last_name_error)
            return
        }

        if (!hospitalName.isValidHospitalName()) {
            SnackbarManager.showMessage(R.string.hospital_name_error)
            return
        }

        if (!address.isValidAddress()){
            SnackbarManager.showMessage(R.string.address_error)
            return
        }

        if (!doctorCode.isValidDoctorCode()){
            SnackbarManager.showMessage(R.string.doctor_code_error)
            return
        }

        if(!checkBoxValue){
            SnackbarManager.showMessage(R.string.accept_terms)
            return
        }

        signUpInProgress.value = true
        launchCatching {
            accountService.createUserWithEmailAndPassword(email, password)
            val currentUserId = accountService.currentUserId

            val userInfo = UserInfo(
                userId = currentUserId,
                emailAddress = email,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                doctorCode = doctorCode,
                doctorAccount = isDoctorAccount,
                doctorUID = "",
                height = "",
                age = "",
                address = address,
                hospitalName = hospitalName,
                id = ""
                )

            storageService.saveUserInfo(userInfo)
            storageService.addToDoctorList(currentUserId,userInfo)
            //preHealthRepository.insertUserInfo(userInfo)
            openAndPopUp(DOCTOR_HOME_SCREEN, SIGN_UP_SCREEN, currentUserId)

        }
    }




    fun loginClick(navigateToLogin: (String, String) -> Unit) {
        launchCatching {
            val currentUserId = accountService.currentUserId
            navigateToLogin(LOGIN_SCREEN, SIGN_UP_SCREEN)
        }

    }


}