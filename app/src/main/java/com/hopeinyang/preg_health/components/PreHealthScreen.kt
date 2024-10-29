package com.hopeinyang.preg_health.components

import androidx.annotation.StringRes
import com.hopeinyang.preg_health.R

enum class PreHealthScreen (@StringRes val title: Int){
    HomeScreen(title = R.string.app_name),
    WeightScreen(title = R.string.weight),
    AddWeightScreen(title = R.string.weightMeasurement),
    WeightDetailScreen(title = R.string.weight),
    WeightMeasurement (title = R.string.weightMeasurement),
    UserInfoScreen (title = R.string.userInfo),
    SignUpScreen(title = R.string.signup),
    Settings(title = R.string.settings),
    LoginScreen(title = R.string.login),

}