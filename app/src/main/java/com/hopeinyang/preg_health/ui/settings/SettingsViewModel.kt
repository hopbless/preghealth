package com.hopeinyang.preg_health.ui.settings

import androidx.lifecycle.SavedStateHandle
import com.hopeinyang.preg_health.*
import com.hopeinyang.preg_health.common.ext.*
import com.hopeinyang.preg_health.common.snackbar.SnackbarManager
import com.hopeinyang.preg_health.data.dto.UserInfo
import com.hopeinyang.preg_health.data.service.LogService
import com.hopeinyang.preg_health.data.service.AccountService
import com.hopeinyang.preg_health.data.service.StorageService

import com.hopeinyang.preg_health.navigation.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService

) : MainViewModel(logService) {


    //val uiState = accountService.currentUser.map { SettingsUiState(isAnonymousAccount = it.isAnonymous) }
    var uiState = MutableStateFlow(SettingsUiState())
        private set



    private val phoneNumber
        get() = uiState.value.phoneNumber

    private val age
        get() = uiState.value.age

    private val height
        get() = uiState.value.height

    private val firstName
        get() = uiState.value.firstName

    private val lastName
        get() = uiState.value.lastName
    private val address
        get() = uiState.value.address

    private val hospitalName
        get() = uiState.value.hospitalName

    init {
        launchCatching {
            val userId = checkNotNull(savedStateHandle.get<String>("userId"))
            //val userId = accountService.currentUserId

            val currentUserFlow = storageService.getUserInfoFlow(userId)
            val doctorsList = storageService.getDoctorsList()


            combine(currentUserFlow, doctorsList){userInfoResource, docList ->
                val userData = userInfoResource.data
                val doctors = docList.data ?: emptyList()


                    SettingsUiState(
                        firstName = userData?.firstName ?: "",
                        lastName = userData?.lastName ?: "",
                        address = userData?.address ?: "",
                        emailAddress = userData?.emailAddress ?: "",
                        phoneNumber = userData?.phoneNumber ?: "",
                        height = userData?.height ?: "",
                        age = userData?.age ?: "",
                        isDoctorSelected = userData?.doctorSelected ?: false,
                        hospitalName = userData?.hospitalName ?: "",
                        userId = userId,
                        doctorUID = userData?.doctorUID ?: "",
                        isDoctorAccount = userData?.doctorAccount ?: false,
                        doctorsList = doctors

                    )


            }.collect{uiState.value = it}

        }
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

    fun onPhoneNumberChange(newValue: String) {
        uiState.value = uiState.value.copy(phoneNumber = newValue)
    }

    fun onHeightChange(newValue: String) {
        uiState.value = uiState.value.copy(height = newValue)
    }

    fun onHospitalNameChange(newValue: String){
        uiState.value = uiState.value.copy(hospitalName = newValue)
    }

    fun onDoctorSelected(selected: Boolean, doctorUID: String) {
        launchCatching (snackbar = true){

            if (selected && doctorUID.isNotEmpty()){
                val userInfo = UserInfo(
                    emailAddress = uiState.value.emailAddress,
                    firstName = uiState.value.firstName,
                    lastName = uiState.value.lastName,
                    height = uiState.value.height,
                    age = uiState.value.age,
                    address = uiState.value.address,
                    phoneNumber = uiState.value.phoneNumber,
                    doctorSelected = true,
                    userId = uiState.value.userId,
                    doctorUID = doctorUID,
                    doctorAccount = uiState.value.isDoctorAccount,
                    id = uiState.value.id

                )
                val userDoctorUpdate = mapOf(
                    "doctorSelected" to true,
                    "doctorUID" to doctorUID
                )
                storageService.addPatientToDoctor(
                    doctorUID,
                    uiState.value.userId,
                    userInfo,
                    userDoctorUpdate
                )



            }

        }

        uiState.value = uiState.value.copy(
            isDoctorSelected = true,
            doctorUID = doctorUID
        )


    }

    fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)


    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut(restartApp)

        }
    }

    fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.deleteAccount()
            restartApp(LOGIN_SCREEN)
        }
    }

    fun patientUpdateButtonClick(openAndNavigate: (String, String) -> Unit) {

        if (!age.isValidAge()) {
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
        launchCatching {



            val userUpdate = mapOf(
                "firstName" to uiState.value.firstName,
                "lastName" to uiState.value.lastName,
                "height" to uiState.value.height,
                "age" to uiState.value.age,
                "address" to uiState.value.address,
                "phoneNumber" to uiState.value.phoneNumber,
                "doctorSelected" to uiState.value.isDoctorSelected,
                "doctorUID" to uiState.value.doctorUID
            )
            storageService.updateUserInfo(uiState.value.userId, userUpdate)

            //preHealthRepository.updateUserInfo(userInfoUpdate)
            openAndNavigate(PATIENT_DATA_NAV, SETTINGS_SCREEN)
        }
    }

    fun doctorUpdateButtonClick(openAndNavigate: (String, String) -> Unit) {
        if (!hospitalName.isValidHospitalName()) {
            SnackbarManager.showMessage(R.string.age_error)
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

        if (!address.isValidAddress()) {
            SnackbarManager.showMessage(R.string.address_error)
            return
        }
        launchCatching {

            val doctorUpdate = mapOf(
                "firstName" to uiState.value.firstName,
                "lastName" to uiState.value.lastName,
                "hospitalName" to uiState.value.hospitalName,
                "address" to uiState.value.address,
                "phoneNumber" to uiState.value.phoneNumber
            )
            storageService.updateDoctorInfo(uiState.value.userId, doctorUpdate)
            openAndNavigate(DOCTOR_HOME_SCREEN, SETTINGS_SCREEN)
        }

    }


}