package com.hopeinyang.preg_health.ui.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoginInProgress: Boolean = false,
    val isDoctorAccount:Boolean? = null
)
