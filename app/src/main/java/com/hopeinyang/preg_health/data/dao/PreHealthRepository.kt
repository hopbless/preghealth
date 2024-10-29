package com.hopeinyang.preg_health.data.dao

import com.hopeinyang.preg_health.data.dto.*
import kotlinx.coroutines.flow.Flow

interface PreHealthRepository {
    fun getUserInfo(): Flow<UserInfo>

    fun getAllWeight(): Flow<List<Weight>>

    fun getWeightItem(dateString: String): Flow<Weight?>

    fun getAllBloodSugar(): Flow<List<BloodSugar>>

    fun getBloodSugarItem(dateString: String): Flow<BloodSugar?>

    fun getAllHeartRate(): Flow<List<HeartRate>>

    fun getHeartRateItem(dateString: String): Flow<HeartRate?>

    fun getAllBloodPressure(): Flow<List<BloodPressure>>

    fun getBloodPressureItem(dateString: String): Flow<BloodPressure?>

    suspend fun insertWeight(weight: Weight)
    suspend fun insertBloodPressure(bloodPressure: BloodPressure)
    suspend fun insertBloodSugar(bloodSugar: BloodSugar)
    suspend fun insertHeartRate(heartRate: HeartRate)
    suspend fun insertUserInfo(userInfo: UserInfo)

    suspend fun deleteWeight(weight: Weight)
    suspend fun deleteBloodPressure(bloodPressure: BloodPressure)
    suspend fun deleteBloodSugar(bloodSugar: BloodSugar)
    suspend fun deleteHeartRate(heartRate: HeartRate)
    suspend fun deleteUserInfo(userInfo: UserInfo)

    suspend fun updateWeight(weight: Weight)
    suspend fun updateBloodPressure(bloodPressure: BloodPressure)
    suspend fun updateBloodSugar(bloodSugar: BloodSugar)
    suspend fun updateHeartRate(heartRate: HeartRate)
    suspend fun updateUserInfo(userInfo: UserInfo)




}