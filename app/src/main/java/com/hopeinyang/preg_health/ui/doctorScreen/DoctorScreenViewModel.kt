package com.hopeinyang.preg_health.ui.doctorScreen

import androidx.lifecycle.SavedStateHandle
import com.hopeinyang.preg_health.DOCTOR_HOME_SCREEN
import com.hopeinyang.preg_health.HOME_SCREEN
import com.hopeinyang.preg_health.PATIENT_DATA_NAV
import com.hopeinyang.preg_health.SETTINGS_SCREEN
import com.hopeinyang.preg_health.data.service.AccountService
import com.hopeinyang.preg_health.data.service.LogService
import com.hopeinyang.preg_health.data.service.StorageService
import com.hopeinyang.preg_health.navigation.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class DoctorScreenViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService,
    savedStateHandle: SavedStateHandle

): MainViewModel(logService){


    var uiState = MutableStateFlow(DoctorScreenUiState())
        private set
    private val currentUser = checkNotNull(savedStateHandle.get<String>("userId"))


    init {
        launchCatching {
            val patientsFlow = storageService.getAllPatients()
            combine(patientsFlow){list->
                val patientsList = list[0].data ?: emptyList()
                DoctorScreenUiState(
                    patientList = patientsList
                )

            }.collect{uiState.value = it}

        }



    }

    fun onPatientSelected(userId: String?, navigateTo: (String, String) -> Unit) {
        launchCatching {
            navigateTo(userId!!, PATIENT_DATA_NAV)
        }
    }

    fun menuIconClick(navigateToSettings: (String,) -> Unit) {
        navigateToSettings(SETTINGS_SCREEN,)
    }



}