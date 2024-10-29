package com.hopeinyang.preg_health.ui.addItem

import androidx.lifecycle.SavedStateHandle
import com.hopeinyang.preg_health.ADD_ITEM_SCREEN
import com.hopeinyang.preg_health.ADD_WEIGHT_SCREEN
import com.hopeinyang.preg_health.WEIGHT_SCREEN
import com.hopeinyang.preg_health.data.dto.*
import com.hopeinyang.preg_health.data.service.LogService
import com.hopeinyang.preg_health.data.service.StorageService
import com.hopeinyang.preg_health.navigation.MainViewModel
import com.hopeinyang.preg_health.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService,
    logService: LogService

): MainViewModel(logService){
    private val userId = checkNotNull(savedStateHandle["userId"]).toString()
    private val category = checkNotNull(savedStateHandle["category"]).toString()

    private var _uiState = MutableStateFlow(AddItemUiState())
    val uiState:StateFlow<AddItemUiState> = _uiState.asStateFlow()


    init {
        launchCatching {
            when(category){
                WEIGHT -> {

                    val weightFlow = storageService.getAllItem<Weight>(WEIGHT,userId, "weightDate")
                    combine(weightFlow){weight->
                        val newWeight = weight[0].data as List<Weight>

                        val lastWeight = if (newWeight.isNullOrEmpty()) "--" else newWeight.last().weight

                        AddItemUiState(
                            weight = lastWeight,
                            category = WEIGHT
                        )

                    }
                        .collect{_uiState.value = it}

                }
                BLOOD_PRESSURE -> {
                    val bloodPressureFlow = storageService.getAllItem<BloodPressure>(BLOOD_PRESSURE,userId, "bloodPressureDate")
                    combine(bloodPressureFlow){bloodPressure->

                        val diastolic = if (bloodPressure[0].data.isNullOrEmpty()) "--" else bloodPressure[0].data?.last()?.diastolic!!
                        val systolic = if (bloodPressure[0].data.isNullOrEmpty()) "--" else bloodPressure[0].data?.last()?.systolic!!
                       AddItemUiState(
                           bpDiastolic =  diastolic,
                           bpSystolic = systolic,
                           category = BLOOD_PRESSURE
                       )

                    }
                        .collect{_uiState.value = it}

                }
                BLOOD_SUGAR ->{

                    val bloodSugarFlow = storageService.getAllItem<BloodSugar>(BLOOD_SUGAR,userId, "bloodSugarDate")
                    combine(bloodSugarFlow){bloodSugar->

                        val bs = if (bloodSugar[0].data.isNullOrEmpty()) "--" else bloodSugar[0].data?.last()?.bloodSugar!!
                        AddItemUiState(
                            bloodSugar = bs,
                            category = BLOOD_SUGAR
                        )

                    }
                        .collect{_uiState.value = it}

                }
                HEART_RATE -> {

                    val heartRateFlow = storageService.getAllItem<HeartRate>(HEART_RATE,userId, "heartRateDate")
                    combine(heartRateFlow){heartRate->

                        val hr = if (heartRate[0].data.isNullOrEmpty()) "--" else heartRate[0].data?.last()?.heartRate!!
                        AddItemUiState(
                            heartRate = hr,
                            category = HEART_RATE
                        )

                    }
                        .collect{_uiState.value = it}

                }
                BODY_TEMP -> {

                    val bodyTempFlow = storageService.getAllItem<BodyTemp>(BODY_TEMP,userId, "bodyTempDate")
                    combine(bodyTempFlow){bodyTemp->

                        val bt = if (bodyTemp[0].data.isNullOrEmpty()) "--" else bodyTemp[0].data?.last()?.bodyTemp!!
                        AddItemUiState(
                            bodyTemp = bt,
                            category = BODY_TEMP
                        )

                    }
                        .collect{_uiState.value = it}

                }
            }



        }
    }

    fun onNewWeightValue(newValue: String){
        _uiState.value = _uiState.value.copy( weight = newValue)

    }


    fun onClosedButtonClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            when(category){
                WEIGHT -> {
                    openAndPopUp(WEIGHT_SCREEN, ADD_ITEM_SCREEN)

                }
                BLOOD_PRESSURE -> {
                    openAndPopUp(BLOOD_PRESSURE, ADD_ITEM_SCREEN)

                }
                BLOOD_SUGAR -> {
                    openAndPopUp(BLOOD_SUGAR, ADD_ITEM_SCREEN)
                }
                HEART_RATE -> {
                    openAndPopUp(HEART_RATE, ADD_ITEM_SCREEN)
                }
                BODY_TEMP -> {
                    openAndPopUp(BODY_TEMP, ADD_ITEM_SCREEN)
                }
            }

        }
    }

    fun saveAndNavigate(
        dateTime: ZonedDateTime,
        saveAndPopUP: (String, String) -> Unit
    ) {
        launchCatching {
            when(category){
                WEIGHT -> {
                    val ww = Weight(
                        weightTime = Utils.fromDateTimeToTime(dateTime),
                        weightDate = Utils.fromDateTimeToDate(dateTime),
                        weight = uiState.value.weight
                    )
                    storageService.saveItem(category,userId, ww)
                    saveAndPopUP(WEIGHT_SCREEN, ADD_ITEM_SCREEN)

                }
                BLOOD_PRESSURE ->{
                    val bp = BloodPressure(
                        bloodPressureDate = Utils.fromDateTimeToDate(dateTime),
                        bloodPressureTime = Utils.fromDateTimeToTime(dateTime),
                        diastolic = uiState.value.bpDiastolic,
                        systolic = uiState.value.bpSystolic
                    )
                }
            }





        }
    }

    fun onNewBloodPressure(diastolic: String, systolic:String) {
        _uiState.value = _uiState.value.copy(
            bpDiastolic = diastolic,
            bpSystolic = systolic
        )

    }

    fun onNewBloodSugar(bs: String) {
        _uiState.value = _uiState.value.copy(
            bloodSugar = bs
        )

    }

    fun onNewHeartRate(hr: String) {
        _uiState.value = _uiState.value.copy(
            heartRate = hr
        )
    }

    fun onNewBodyTemp(newBodyTemp: String) {
        _uiState.value = _uiState.value.copy(
          bodyTemp = newBodyTemp
        )
    }

    companion object{
        private const val USER_ID_FIELD = "userId"
        private const val WEIGHT = "Weight"
        private const val BLOOD_PRESSURE = "BloodPressure"
        private const val HEART_RATE = "HeartRate"
        private const val BLOOD_SUGAR = "BloodSugar"
        private const val BODY_TEMP = "BodyTemp"
        private const val USER_INFO = "UserInfo"
    }
}