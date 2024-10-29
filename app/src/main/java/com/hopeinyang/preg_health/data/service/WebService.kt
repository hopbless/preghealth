package com.hopeinyang.preg_health.data.service


import retrofit2.Response

interface WebService {
    suspend fun predictRiskLevel (
       age:String, bpSystolic:String, bpDiastolic: String,
       bloodSugar:String,bodyTemp:String, heartRate:String
    ):Response<Any>
}