package com.hopeinyang.preg_health.ui.signup

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val age: String = "",
    val weekNo:String = "",
    val height: String = "",
    val checkBoxValue:Boolean = false,
    val isDoctorAccount:Boolean = false,
    val doctorCode:String = "",
    val id: String = "",
    val hospitalName:String = "",

)