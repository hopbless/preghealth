package com.hopeinyang.preg_health.data.service


import retrofit2.Response
import retrofit2.http.*

interface WebServerAPI {

    @FormUrlEncoded
    @POST("predict")
    suspend fun predictRisk(@Field("age") age: String,
                            @Field("systolic") bpSystolic: String,
                            @Field("diastolic") bpDiastolic: String,
                            @Field("bloodSugar") bloodSugar: String,
                            @Field("bodyTemp") bodyTemp: String,
                            @Field("heartRate") heartRate: String): Response<Any>

}