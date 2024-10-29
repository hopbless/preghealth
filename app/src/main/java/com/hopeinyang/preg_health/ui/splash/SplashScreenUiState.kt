package com.hopeinyang.preg_health.ui.splash



data class SplashScreenUiState(
    val isDoctorAccount: Boolean = false,
    val isUserRegistered: Boolean = false,
    val showError:Boolean = false,
    val userId: String = "",
    val continueButtonClicked: Boolean = false


)
