package com.hopeinyang.preg_health.ui.doctorScreen

import com.hopeinyang.preg_health.data.dto.UserInfo

data class DoctorScreenUiState(
    val patientList: List<UserInfo> = emptyList()
)