package com.hopeinyang.preg_health.ui.homeScreen

import androidx.compose.ui.graphics.Color
import com.hopeinyang.preg_health.data.dto.*


data class HomeScreenState(
    val initialValue1: String = "",
    val initialValue2: String = "",
    val bpDiastolic: String = "",
    val bpSystolic: String = "",
    val heartRate: String = "",
    val bloodSugar: String = "",
    val height:Float = 0.0f,
    val weightDate: String = " ",
    val bpDate: String = "",
    val hrDate: String = "",
    var bsDate: String = "",
    val btDate: String = "",
    val latestWeight:String = "",
    val latestBodyTemp: String = "",
    val userId: String? = null,
    val age:String = "",
    val category: String ="",
    val weightList: List<Weight> = emptyList(),
    val bloodPressureList:List<BloodPressure> = emptyList(),
    val bloodSugarList:List<BloodSugar?> = emptyList(),
    val heartRateList:List<HeartRate> = emptyList(),
    val bodyTempList:List<BodyTemp> = emptyList(),
    val userInfo: UserInfo? = null,
    val doctorAccount: Boolean = false,
    val displayValue:String = "",
    val displayDate: String ="",
    val dateList: List<String> = emptyList(),
    val indices: List<Int> = emptyList(),
    val timeAndValue: List<Map<String, String>>  = emptyList(),
    val currentTimeAndValue: Map<String, String> = emptyMap(),
    val predictionResult: Map<String, Color> = mapOf(" " to Color.Transparent),
    val weightResultAndColor: Map<String, Color> = mapOf(" " to Color.Transparent),
    val bpResultAndColor: Map<String, Color> = mapOf(" " to Color.Transparent),
    val bsResultAndColor: Map<String, Color> = mapOf(" " to Color.Transparent),
    val btResultAndColor: Map<String, Color> = mapOf(" " to Color.Transparent),
    val hrResultAndColor: Map<String, Color> = mapOf(" " to Color.Transparent),
    val predictionResultAndColor: Map<String, Color> = mapOf(" " to Color.Transparent)



    )
