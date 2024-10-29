package com.hopeinyang.preg_health.ui.settings

import com.hopeinyang.preg_health.data.dto.UserInfo


data class SettingsUiState(
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val height: String = "",
    val age: String = "",
    val emailAddress: String = " ",
    val isDoctorSelected: Boolean = false,
    val hospitalName:String = "",
    val userId:String = " ",
    val isDoctorAccount:Boolean =  false,
    val doctorsList: List<UserInfo> = emptyList(),
    val id:String ="",
    val doctorUID: String =""
)