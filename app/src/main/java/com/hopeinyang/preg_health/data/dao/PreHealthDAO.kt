package com.hopeinyang.preg_health.data.dao

import androidx.room.*
import com.hopeinyang.preg_health.data.dto.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PreHealthDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo:UserInfo)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeight(weight:Weight)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBloodPressure(bloodPressure: BloodPressure)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBloodSugar(bloodSugar: BloodSugar)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeartRate(heartRate: HeartRate)

    @Update
    suspend fun updateUserInfo(userInfo: UserInfo)

    @Update
    suspend fun updateWeight(weight: Weight)

    @Update
    suspend fun updateHeartRate(heartRate: HeartRate)

    @Update
    suspend fun updateBloodSugar(bloodSugar: BloodSugar)

    @Update
    suspend fun updateBloodPressure(bloodPressure: BloodPressure)

    @Delete
    suspend fun deleteUserInfo(userInfo: UserInfo)

    @Update
    suspend fun deleteWeight(weight: Weight)

    @Update
    suspend fun deleteHeartRate(heartRate: HeartRate)

    @Update
    suspend fun deleteBloodSugar(bloodSugar: BloodSugar)

    @Update
    suspend fun deleteBloodPressure(bloodPressure: BloodPressure)

    @Query("SELECT * from weight ORDER BY weightDate ASC")
    fun getAllWeightItems(): Flow<List<Weight>>

    @Query("SELECT * from weight WHERE weightDate = :dateString")
    fun getWeightItem(dateString: String): Flow<Weight>

    @Query("SELECT * from bloodpressure ORDER BY bloodPressureDate ASC")
    fun getAllBloodPressureItems(): Flow<List<BloodPressure>>

    @Query("SELECT * from bloodpressure WHERE bloodPressureDate = :dateString")
    fun getBloodPressureItem(dateString: String): Flow<BloodPressure>

    @Query("SELECT * from bloodsugar ORDER BY bloodSugarDate ASC")
    fun getAllBloodSugarItems(): Flow<List<BloodSugar>>

    @Query("SELECT * from bloodsugar WHERE bloodSugarDate = :dateString")
    fun getBloodSugarItem(dateString: String): Flow<BloodSugar>

    @Query("SELECT * from heartrate ORDER BY heartRateDate ASC")
    fun getAllHeartRateItems(): Flow<List<HeartRate>>

    @Query("SELECT * from heartrate WHERE heartRateDate = :dateString")
    fun getHeartRateItem(dateString: String): Flow<HeartRate>

    @Query("SELECT * from userinfo")
    fun getUserInfoItems(): Flow<UserInfo>


}