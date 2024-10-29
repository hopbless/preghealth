package com.hopeinyang.preg_health.data.service.impl

import android.util.Log
import androidx.core.os.trace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.common.snackbar.SnackbarManager
import com.hopeinyang.preg_health.data.dto.*
import com.hopeinyang.preg_health.data.service.AccountService
import com.hopeinyang.preg_health.data.service.StorageService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class StorageServiceImpl @Inject constructor(
    private val auth: AccountService,
    //private val preHealthRepository: PreHealthRepository,
    private val firestore: FirebaseFirestore
): StorageService{


    override fun getUserInfoFlow(userId:String): Flow<Resource<UserInfo?>> = callbackFlow {
        val userCollection = firestore.collection(USER_INFO).document(userId)

        val snapshotListener = userCollection.addSnapshotListener { value, error ->
            val response = if (error == null && value !=null){
                val data = UserInfo.toObject(value)
                Resource.success(data)
            }else{
                Resource.error(error.toString(), null)
            }
            trySend(response)
        }
        awaitClose { snapshotListener.remove() }
    }


//


    override fun<T> getAllItem(itemDir: String, userId: String, sortCriteria: String): Flow<Resource<List<T?>>> = callbackFlow {
        val collection = firestore.collection(PATIENT_DATA).document(userId)
            .collection(itemDir)
        //val snapshotListener = collection.orderBy(sortCriteria, Query.Direction.DESCENDING)
        val snapshotListener = collection
            .addSnapshotListener { value, error ->

            val response = if (error == null && value != null){
               val data = value.documents.map {documentSnapshot ->
                   when(itemDir){
                       WEIGHT -> {
                           Weight.toObject(documentSnapshot)
                       }
                       BLOOD_PRESSURE -> {
                           BloodPressure.toObject(documentSnapshot)
                       }
                       HEART_RATE ->{
                           HeartRate.toObject(documentSnapshot)
                       }
                       BODY_TEMP -> {
                           BodyTemp.toObject(documentSnapshot)

                       }
                       BLOOD_SUGAR-> {
                           BloodSugar.toObject(documentSnapshot)

                       }


                       else -> {
                           Resource.error(error.toString(), null)
                       }
                   }

                }
                Resource.success(data)

            } else {
                Resource.error(error.toString(), null)

            }
            trySend(response as Resource<List<T?>>)
        }
        awaitClose { snapshotListener.remove() }
    }

    override fun getAllBloodPressure(userId: String): Flow<Resource<List<BloodPressure>>> = callbackFlow {
        val weightCollection = firestore.collection(PATIENT_DATA).document(userId)
            .collection(BLOOD_PRESSURE)
        val snapshotListener = weightCollection.orderBy("bpDate", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->

                val response = if (error == null && value != null){
                    val data = value.documents.map {documentSnapshot ->
                        Weight.toObject(documentSnapshot)
                    }
                    Resource.success(data!!)

                } else {
                    Resource.error(error.toString(), null)

                }
                trySend(response as Resource<List<BloodPressure>>)
            }
        awaitClose { snapshotListener.remove() }
    }


    override fun getWeightItem(dateString: String): Flow<Weight?> {
        TODO("Not yet implemented")
    }

    override fun getDoctorsList(): Flow<Resource<List<UserInfo>>> =  callbackFlow {
        val userCollection = firestore.collection(DOCTORS_LIST)

        val snapshotListener = userCollection.addSnapshotListener { value, error ->
            val response = if (error == null && value !=null){

                val data = value.documents.map { doc->
                    UserInfo.toObject(doc)

                }
                Resource.success(data)
            }else{
                Resource.error(error.toString(), null)
            }
            trySend(response as Resource<List<UserInfo>>)
        }
        awaitClose { snapshotListener.remove() }
    }

    override fun getAllPatients(): Flow<Resource<List<UserInfo>>> = callbackFlow {
        val userCollection = firestore.collection(USER_INFO).document(auth.currentUserId)
            .collection(PATIENTS_LIST)

        val snapshotListener = userCollection.addSnapshotListener { value, error ->
            val response = if (error == null && value !=null){

                val data = value.documents.map { doc->
                    UserInfo.toObject(doc)

                }
                Resource.success(data)
            }else{
                Resource.error(error.toString(), null)
            }
            trySend(response as Resource<List<UserInfo>>)
        }
        awaitClose { snapshotListener.remove() }
    }

    override fun getUserInfo(
        userId: String,
        openAndNavigate: (String, String, String)-> Unit,
        isDoctorAccount: (Boolean, UserInfo?, (String, String, String) -> Unit)-> Unit,
        ) {

        var isDoctorAccount: Boolean? = false
        firestore.collection(USER_INFO).document(userId).get()
            .addOnSuccessListener {
                Log.d("Success ", "User data fetched")

                val user = UserInfo.toObject(it)
                isDoctorAccount(true, user, openAndNavigate)
                //isDoctorAccount = user?.doctorAccount
        }
            .addOnFailureListener {
                isDoctorAccount(false, null, openAndNavigate)

            }
    }

    override suspend fun updateUserInfo(userId: String, userInfoUpdate: Map<String, Any>) {
       firestore.collection(USER_INFO).document(userId).update(userInfoUpdate)
           .addOnSuccessListener {
               if (userInfoUpdate["doctorUID"].toString().isNotEmpty()){
                   firestore.collection(USER_INFO)
                       .document(userInfoUpdate["doctorUID"].toString())
                       .collection(PATIENTS_LIST).document(userId).update(userInfoUpdate)
                       .addOnSuccessListener {

                       }
                       .addOnFailureListener {

                       }
               }

           }
           .addOnFailureListener {
           }

    }

    override suspend fun updateDoctorInfo(userId: String, userInfoUpdate: Map<String, Any>) {
        firestore.collection(USER_INFO).document(userId).update(userInfoUpdate)
            .addOnSuccessListener {
                firestore.collection(DOCTORS_LIST).document(userId).update(userInfoUpdate)
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener {

                    }
            }
            .addOnFailureListener {

            }
    }





    override suspend fun addToDoctorList(userId: String, userInfo: UserInfo) {
        trace(ADD_DOCTOR){
            val document = firestore.collection(DOCTORS_LIST).document(userId)
            val user = userInfo.copy(
                id = document.id,
                doctorUID = document.id
            )
                document.set(user)
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }


        }
    }

    override suspend fun addPatientToDoctor(
        doctorUID: String,
        patientUID: String,
        userInfo: UserInfo,
        userDoctorUpdate:Map<String, Any>
    ) {
        val doc = firestore.collection(USER_INFO)
            .document(doctorUID)
            .collection(PATIENTS_LIST)
            .document(patientUID)
        doc.set(userInfo)
            .addOnSuccessListener {
                SnackbarManager.showMessage(R.string.doctoraddedsuccess)
                firestore.collection(USER_INFO).document(patientUID).update(userDoctorUpdate)
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener {

                    }


        }
            .addOnFailureListener {

            }
    }

    override suspend fun saveWeight(weight: Weight) {
            val document = firestore.collection(PATIENT_DATA)
                .document(auth.currentUserId).collection(WEIGHT).document()
                val newWeight = weight.copy(id = document.id)
                document.set(newWeight)

                .addOnSuccessListener {

                }.addOnFailureListener {

                }

    }

    override suspend fun saveBloodPressure(bloodPressure: BloodPressure) {
        val document = firestore.collection(PATIENT_DATA)
            .document(auth.currentUserId).collection(BLOOD_PRESSURE).document()
        val newBp = bloodPressure.copy(id = document.id)
        document.set(newBp)

            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }



    override suspend fun saveUserInfo(userInfo: UserInfo) {
        trace(SAVE_USER_INFO){
            Log.d("userId Hope", auth.currentUserId)
            val document = firestore.collection(USER_INFO)
                .document(auth.currentUserId)
                .set(userInfo)
                .addOnSuccessListener {
                    Log.d("Success!!!", "Write Userinfo successfully")
                }
                .addOnFailureListener {

                }


        }
    }

    override suspend fun <T: Any> saveItem(itemDir: String, userId: String, item: T) {
        val document = firestore.collection(PATIENT_DATA)
            .document(auth.currentUserId).collection(itemDir).document().set(item)
            .addOnSuccessListener {
                Log.d("Success!!!", "Write data successfully")

            }
            .addOnFailureListener {

            }

    }


    override suspend fun deleteWeight(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateWeight(weight: Weight) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val PATIENT_DATA = "PatientsData"
        private const val DOCTORS_LIST = "DoctorsList"
        private const val PATIENTS_LIST = "PatientsList"
        private const val WEIGHT = "Weight"
        private const val BLOOD_PRESSURE = "BloodPressure"
        private const val BLOOD_SUGAR = "BloodSugar"
        private const val BODY_TEMP = "BodyTemp"
        private const val HEART_RATE = "HeartRate"
        private const val USER_INFO = "UserInfo"
        private const val SAVE_WEIGHT_TRACE = "saveWeight"
        private const val SAVE_USER_INFO = "saveUserInfo"
        private const val ADD_DOCTOR = "addDoctor"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }

}