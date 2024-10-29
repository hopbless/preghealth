package com.hopeinyang.preg_health.data.service

import com.hopeinyang.preg_health.data.dto.*
import kotlinx.coroutines.flow.Flow

interface StorageService {



    fun getUserInfoFlow(userId: String): Flow<Resource<UserInfo?>>

    fun <T> getAllItem(itemDir: String, userId:String, sortCriteria: String): Flow<Resource<List<T?>>>
    fun getAllBloodPressure(userId: String):Flow<Resource<List<BloodPressure>>>
    fun getWeightItem(dateString: String): Flow<Weight?>
    fun getDoctorsList(): Flow<Resource<List<UserInfo>>>
    fun getAllPatients():Flow<Resource<List<UserInfo>>>
    fun getUserInfo (userId: String, openAndNavigate: (String, String, String) -> Unit,
                     user: (Boolean, UserInfo?, (String, String, String) -> Unit)-> Unit,)

    suspend fun updateUserInfo(userId: String, userInfoUpdate: Map<String, Any>)
    suspend fun updateDoctorInfo(userId: String, userInfoUpdate:Map<String, Any>)
    suspend fun addToDoctorList(userId: String, userInfo: UserInfo)
    suspend fun addPatientToDoctor(
        doctorUID:String,
        patientUID:String,
        userInfo: UserInfo,
        userDoctorUpdate:Map<String, Any>
    )
    suspend fun saveUserInfo(userInfo: UserInfo)
    suspend fun <T: Any>saveItem(itemDir: String, userId: String, item: T)
    suspend fun saveWeight(weight: Weight)
    suspend fun saveBloodPressure(bloodPressure: BloodPressure)
    suspend fun deleteWeight(id: String)
    suspend fun updateWeight(weight: Weight)



}