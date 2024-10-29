package com.hopeinyang.preg_health.data.service.impl

import com.hopeinyang.preg_health.data.service.WebServerAPI
import com.hopeinyang.preg_health.data.service.WebService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class WebServiceImpl @Inject constructor(
    private val webServerAPI: WebServerAPI

) :  WebService{
    override suspend fun predictRiskLevel(
        age: String,
        bpSystolic: String,
        bpDiastolic: String,
        bloodSugar: String,
        bodyTemp: String,
        heartRate: String
    ): Response<Any> = webServerAPI.predictRisk(age, bpSystolic,bpDiastolic, bloodSugar, bodyTemp, heartRate)

}