package com.hopeinyang.preg_health.data.dao.impl

import com.hopeinyang.preg_health.data.dao.PreHealthDAO
import com.hopeinyang.preg_health.data.dao.PreHealthRepository
import com.hopeinyang.preg_health.data.dto.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreHealthDBRepositoryImpl @Inject constructor(
    private val itemHealthDAO: PreHealthDAO
        ): PreHealthRepository{

    //    fun getUserInfo(): Flow<UserInfo?>  = itemHealthDAO.getUserInfoItems()
//
//    fun getAllWeight(): Flow<List<Weight>> = itemHealthDAO.getAllWeightItems()
//
//    fun getWeightItem(dateString: String): Flow<Weight?> = itemHealthDAO.getWeightItem(dateString)
//
//    fun getAllBloodSugar(): Flow<List<BloodSugar>> = itemHealthDAO.getAllBloodSugarItems()
//
//    fun getBloodSugarItem(dateString: String): Flow<BloodSugar?> = itemHealthDAO.getBloodSugarItem(dateString)
//
//    fun getAllHeartRate(): Flow<List<HeartRate>> = itemHealthDAO.getAllHeartRateItems()
//
//    fun getHeartRateItem(dateString: String): Flow<HeartRate?> = itemHealthDAO.getHeartRateItem(dateString)
//
//    fun getAllBloodPressure(): Flow<List<BloodPressure>> = itemHealthDAO.getAllBloodPressureItems()
//
//    fun getBloodPressureItem(dateString: String): Flow<BloodPressure?> = itemHealthDAO.getBloodPressureItem(dateString)
//
//    suspend fun insertBloodPressure(bloodPressure: BloodPressure) = itemHealthDAO.insertBloodPressure(bloodPressure)
//    suspend fun insertBloodSugar(bloodSugar: BloodSugar) = itemHealthDAO.insertBloodSugar(bloodSugar)
//    suspend fun insertHeartRate(heartRate: HeartRate) = itemHealthDAO.insertHeartRate(heartRate)
//    suspend fun insertWeight(weight: Weight)  = itemHealthDAO.insertWeight(weight)
//    suspend fun insertUserInfo(userInfo: UserInfo) = itemHealthDAO.insertUserInfo(userInfo)
//
//    suspend fun updateBloodPressure(bloodPressure: BloodPressure) =  itemHealthDAO.updateBloodPressure(bloodPressure)
//    suspend fun updateBloodSugar(bloodSugar: BloodSugar) = itemHealthDAO.updateBloodSugar(bloodSugar)
//    suspend fun updateHeartRate(heartRate: HeartRate) = itemHealthDAO.updateHeartRate(heartRate)
//    suspend fun updateWeight(weight: Weight) = itemHealthDAO.updateWeight(weight)
//    suspend fun updateUserInfo(userInfo: UserInfo) = itemHealthDAO.updateUserInfo(userInfo)
//
//    suspend fun deleteBloodPressure(bloodPressure: BloodPressure) =  itemHealthDAO.deleteBloodPressure(bloodPressure)
//    suspend fun deleteBloodSugar(bloodSugar: BloodSugar) = itemHealthDAO.deleteBloodSugar(bloodSugar)
//    suspend fun deleteHeartRate(heartRate: HeartRate) = itemHealthDAO.deleteHeartRate(heartRate)
//    suspend fun deleteWeight(weight: Weight) = itemHealthDAO.deleteWeight(weight)
//    suspend fun deleteUserInfo(userInfo: UserInfo) = itemHealthDAO.deleteUserInfo(userInfo)

    override fun getUserInfo(): Flow<UserInfo> = itemHealthDAO.getUserInfoItems()

    override fun getAllWeight(): Flow<List<Weight>> = itemHealthDAO.getAllWeightItems()

    override fun getWeightItem(dateString: String): Flow<Weight?> = itemHealthDAO.getWeightItem(dateString)

    override fun getAllBloodSugar(): Flow<List<BloodSugar>> = itemHealthDAO.getAllBloodSugarItems()

    override fun getBloodSugarItem(dateString: String): Flow<BloodSugar?> =  itemHealthDAO.getBloodSugarItem(dateString)

    override fun getAllHeartRate(): Flow<List<HeartRate>> = itemHealthDAO.getAllHeartRateItems()

    override fun getHeartRateItem(dateString: String): Flow<HeartRate?> = itemHealthDAO.getHeartRateItem(dateString)

    override fun getAllBloodPressure(): Flow<List<BloodPressure>> = itemHealthDAO.getAllBloodPressureItems()

    override fun getBloodPressureItem(dateString: String): Flow<BloodPressure?> = itemHealthDAO.getBloodPressureItem(dateString)
    override suspend fun insertWeight(weight: Weight) = itemHealthDAO.insertWeight(weight)



    override suspend fun insertBloodPressure(bloodPressure: BloodPressure) = itemHealthDAO.insertBloodPressure(bloodPressure)

    override suspend fun insertBloodSugar(bloodSugar: BloodSugar) = itemHealthDAO.updateBloodSugar(bloodSugar)

    override suspend fun insertHeartRate(heartRate: HeartRate) = itemHealthDAO.insertHeartRate(heartRate)

    override suspend fun insertUserInfo(userInfo: UserInfo) = itemHealthDAO.insertUserInfo(userInfo)

    override suspend fun deleteWeight(weight: Weight) = itemHealthDAO.deleteWeight(weight)

    override suspend fun deleteBloodPressure(bloodPressure: BloodPressure) = itemHealthDAO.deleteBloodPressure(bloodPressure)
    override suspend fun deleteBloodSugar(bloodSugar: BloodSugar) = itemHealthDAO.deleteBloodSugar(bloodSugar)

    override suspend fun deleteHeartRate(heartRate: HeartRate) = itemHealthDAO.deleteHeartRate(heartRate)

    override suspend fun deleteUserInfo(userInfo: UserInfo) = itemHealthDAO.deleteUserInfo(userInfo)

    override suspend fun updateWeight(weight: Weight) = itemHealthDAO.updateWeight(weight)

    override suspend fun updateBloodPressure(bloodPressure: BloodPressure) = itemHealthDAO.deleteBloodPressure(bloodPressure)

    override suspend fun updateBloodSugar(bloodSugar: BloodSugar) = itemHealthDAO.deleteBloodSugar(bloodSugar)

    override suspend fun updateHeartRate(heartRate: HeartRate) = itemHealthDAO.deleteHeartRate(heartRate)

    override suspend fun updateUserInfo(userInfo: UserInfo) =  itemHealthDAO.deleteUserInfo(userInfo)


}