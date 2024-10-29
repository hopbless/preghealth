package com.hopeinyang.preg_health.ui.homeScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hopeinyang.preg_health.*
import com.hopeinyang.preg_health.common.ext.toDate
import com.hopeinyang.preg_health.common.ext.toTime
import com.hopeinyang.preg_health.data.dto.*
import com.hopeinyang.preg_health.data.service.AccountService
import com.hopeinyang.preg_health.data.service.LogService
import com.hopeinyang.preg_health.data.service.StorageService
import com.hopeinyang.preg_health.data.service.WebService
import com.hopeinyang.preg_health.navigation.MainViewModel
import com.hopeinyang.preg_health.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject
@HiltViewModel
open class HomeScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService,
    private val accountService: AccountService,
    private val webService: WebService,
    logService: LogService,

) : MainViewModel(logService) {

    private val userId = checkNotNull(savedStateHandle.get<String>("userId"))


    private val _state = MutableStateFlow(HomeScreenState())

    val state: StateFlow<HomeScreenState>
        get() = _state

    private val currentUser = accountService.currentUserId

    private val weightList
        get() = state.value.weightList

    private val bloodPressureList
        get() = state.value.bloodPressureList

    private val bloodSugarList
        get() = state.value.bloodSugarList

    private val heartRateList
        get() = state.value.heartRateList

    private val bodyTempList
        get() = state.value.bodyTempList

    init {
        viewModelScope.launch {


            val patientInfoFlow = storageService.getUserInfoFlow(userId)
            val doctorInfoFlow = storageService.getUserInfoFlow(currentUser)
            val weightFlow = storageService.getAllItem<Weight>(WEIGHT, userId, "weightDate")
            val heartRateFlow = storageService.getAllItem<HeartRate>(HEART_RATE, userId, "heartRateDate")
            val bloodPressureFlow = storageService.getAllItem<BloodPressure>(BLOOD_PRESSURE, userId, "bloodPressureDate")
            val bloodSugarFlow = storageService.getAllItem<BloodSugar>(BLOOD_SUGAR, userId, "bloodSugarDate")
            val bodyTempFlow = storageService.getAllItem<BodyTemp>(BODY_TEMP, userId, "bodyTempDate")


            combine(
                patientInfoFlow,weightFlow, heartRateFlow, bloodPressureFlow,
                bloodSugarFlow, bodyTempFlow, doctorInfoFlow,
            ) {resources:Array<Resource<Any?>> ->

                val userInfo = resources[0].data as UserInfo
                val weight = resources[1].data as List<Weight>
                val heartRate = resources[2].data as List<HeartRate>
                val bloodPressure = resources[3].data as List<BloodPressure>
                val bloodSugar = resources[4].data as List<BloodSugar?>
                val bodyTemp = resources[5].data as List<BodyTemp>
                val doctorInfo = resources[6].data as UserInfo




                val bs =
                    (if (!bloodSugar.isNullOrEmpty()) bloodSugar
                        .sortedWith(compareBy({it?.bloodSugarDate?.toDate()!!},
                            {it?.bloodSugarTime?.toTime()!!})).last()?.bloodSugar!! else "--")

                val bpDiastolic =
                    (if (bloodPressure.isNotEmpty()) bloodPressure
                        .sortedWith(compareBy({it.bloodPressureDate.toDate()},
                            {it.bloodPressureTime.toTime()})).last().diastolic else "--")

                val bpSystolic =
                    (if (bloodPressure.isNotEmpty()) bloodPressure
                        .sortedWith(compareBy({it.bloodPressureDate.toDate()},
                            {it.bloodPressureTime.toTime()})).last().systolic else "--")
                val hr =
                    (if (heartRate.isNotEmpty())heartRate
                        .sortedWith(compareBy({it.heartRateDate.toDate()},
                            {it.heartRateTime.toTime()})).last().heartRate else "--")
                val bt =
                    (if (bodyTemp.isNotEmpty()) bodyTemp
                        .sortedWith(compareBy({it.bodyTempDate.toDate()},
                            {it.bodyTempTime.toTime()})).last().bodyTemp else "--")

                val newWeight = if (weight.isNotEmpty()) weight
                    .sortedWith(compareBy({it.weightDate.toDate()},
                        {it.weightTime.toTime()})).last().weight else "--"

                val bsDate =
                    (if (bloodSugar.isNotEmpty()) bloodSugar.sortedBy {
                        it?.bloodSugarDate?.toDate()!! }.last()?.bloodSugarDate!! else "No data to display")
                val bpDate =
                    (if (bloodPressure.isNotEmpty()) bloodPressure.sortedBy {
                        it.bloodPressureDate.toDate() }.last().bloodPressureDate else " No data to display")

                val hrDate =
                    (if (heartRate.isNotEmpty()) heartRate.sortedBy {
                        it.heartRateDate.toDate()}.last().heartRateDate else "No data to display")

                val weightDate = if (weight.isNotEmpty()) weight.maxByOrNull {
                    it.weightDate.toDate()}!!.weightDate else " No data to display"

                val btDate =
                    (if (bodyTemp.isNotEmpty()) bodyTemp.sortedBy {
                        it.bodyTempDate.toDate()}.last().bodyTempDate else " No data to display")


                val height = userInfo.height.toFloat()
                val pred = userInfo.result

                val weightResult = if (newWeight !="--")
                    Utils.getWeightResult(newWeight, height) else mapOf(" " to Color.Transparent)
                val bpResult = Utils.getBloodPressureResult(bpDiastolic, bpSystolic)
                val bsResult = Utils.getBloodSugarResult(bs)
                val hrResult = Utils.getHeartResult(hr)
                val btResult = Utils.getBodyTempResult(bt)


                val age = userInfo.age
                val predictionResult = Utils.getPredictionResult(pred)
                predictRiskLevel(age, bpSystolic, bpDiastolic,  bs, bt,  hr)



                HomeScreenState(
                    weightDate = weightDate,
                    heartRate = hr,
                    bloodSugar = bs,
                    bpDiastolic = bpDiastolic ,
                    bpSystolic = bpSystolic ,
                    latestWeight = newWeight,
                    latestBodyTemp = bt,
                    bpDate = bpDate,
                    bsDate = bsDate,
                    hrDate = hrDate,
                    btDate = btDate,
                    userId = userId,
                    weightList = weight,
                    bloodPressureList = bloodPressure,
                    bloodSugarList = bloodSugar,
                    heartRateList = heartRate,
                    bodyTempList = bodyTemp,
                    doctorAccount = doctorInfo.doctorAccount,
                    predictionResult = predictionResult,
                    weightResultAndColor = weightResult,
                    bpResultAndColor = bpResult,
                    bsResultAndColor = bsResult,
                    hrResultAndColor = hrResult,
                    btResultAndColor = btResult,
                    height = height

                )


            }.collect { _state.value = it }

        }
    }


    fun onCardClick(category: String, navigateTo: (String,) -> Unit) {

        launchCatching {
            _state.value = _state.value.copy(category = category)
            when(category){
                WEIGHT ->{

                    launchCatching {
                        if (weightList.isNotEmpty()){
                            val newDate = mutableListOf<String>()
                            val newTimeAndValue = mutableListOf<Map<String, String>>()
                            val distinct = weightList.distinctBy { it.weightDate }
                            val distinctDate = distinct.sortedWith(
                                compareBy({it.weightDate.toDate()},{it.weightTime.toTime()}))

                            val indices = distinctDate.indices.toList()

                            distinctDate.forEach { distinctWeightItem ->
                                newDate.add(distinctWeightItem.weightDate)
                                val newMap = mutableMapOf<String, String>()

                                val sortedWeightList = weightList.sortedWith(
                                    compareBy({it.weightDate.toDate()},
                                    {it.weightTime.toTime()}))

                                sortedWeightList.forEach { weightItemFromFlow ->
                                    if (distinctWeightItem.weightDate == weightItemFromFlow.weightDate) {
                                        newMap[weightItemFromFlow.weightTime] =
                                            weightItemFromFlow.weight
                                    }

                                }
                                newTimeAndValue.add(newMap)

                            }


                            _state.value = _state.value.copy(
                                dateList = newDate,
                                timeAndValue = newTimeAndValue,
                                currentTimeAndValue = newTimeAndValue.last(),
                                displayDate = newDate.last(),
                                displayValue = newTimeAndValue.last()[newTimeAndValue.last().keys.last()].toString(),
                                indices = indices,

                                )

                        }
                        navigateTo(DETAIL_ITEM_SCREEN)

                    }


                }
                BLOOD_PRESSURE->{
                    launchCatching {

                        if (bloodPressureList.isNotEmpty()){

                            val newDate = mutableListOf<String>()
                            val newTimeAndValue = mutableListOf<Map<String, String>>()
                            val distinct = bloodPressureList.distinctBy { it.bloodPressureDate }
                            val distinctDate = distinct.sortedWith(
                                compareBy({it.bloodPressureDate.toDate()},{it.bloodPressureTime.toTime()})
                            )
                            val sortedBPList = bloodPressureList.sortedWith(
                                compareBy({it.bloodPressureDate.toDate()},
                                    {it.bloodPressureTime.toTime()}))


                            val indices = distinctDate.indices.toList()
                            distinctDate.forEach { distinctBloodPressureItem->
                                newDate.add(distinctBloodPressureItem.bloodPressureDate)
                                val newBPMap = mutableMapOf<String, String>()

                                sortedBPList.forEach {bpItemFromFlow ->
                                    if (distinctBloodPressureItem.bloodPressureDate == bpItemFromFlow.bloodPressureDate ){
                                        newBPMap[bpItemFromFlow.bloodPressureTime] =
                                            "${bpItemFromFlow.systolic}/${bpItemFromFlow.diastolic}"

                                    }

                                }
                                newTimeAndValue.add(newBPMap)

                            }
                            _state.value = _state.value.copy(
                                dateList = newDate,
                                timeAndValue = newTimeAndValue,
                                currentTimeAndValue = newTimeAndValue.last(),
                                displayDate = newDate.last(),
                                displayValue = newTimeAndValue.last()[newTimeAndValue.last().keys.last()].toString(),
                                indices = indices,

                            )
                        }

                        navigateTo(DETAIL_ITEM_SCREEN)

                    }

                }
                BLOOD_SUGAR ->{
                    launchCatching {
                        if (!bloodSugarList.isNullOrEmpty()){

                            val newDate = mutableListOf<String>()
                            val newTimeAndValue = mutableListOf<Map<String, String>>()


                            val distinct = bloodSugarList.distinctBy { it?.bloodSugarDate!! }
                            val distinctDate = distinct.sortedWith(
                                compareBy({it?.bloodSugarDate?.toDate()!!},
                                    {it?.bloodSugarTime?.toTime()!!}))

                            val indices = distinctDate.indices.toList()
                            val sortedBloodSugarList = bloodSugarList.sortedWith(
                                compareBy({it?.bloodSugarDate?.toDate()!!},
                                    {it?.bloodSugarTime?.toTime()!!}))

                            distinctDate.forEach { distinctBsItem->
                                newDate.add(distinctBsItem?.bloodSugarDate!!)
                                val newMap = mutableMapOf<String, String>()
                                sortedBloodSugarList.forEach {bsItemFromFlow ->
                                    if (distinctBsItem.bloodSugarDate == bsItemFromFlow?.bloodSugarDate!! ){
                                        newMap[bsItemFromFlow?.bloodSugarTime!!] = bsItemFromFlow.bloodSugar
                                    }

                                }
                                newTimeAndValue.add(newMap)

                            }
                            _state.value = _state.value.copy(
                                dateList = newDate,
                                timeAndValue = newTimeAndValue,
                                currentTimeAndValue = newTimeAndValue.last(),
                                displayDate = newDate.last(),
                                displayValue = newTimeAndValue.last()[newTimeAndValue.last().keys.last()].toString(),
                                indices = indices,

                                )
                        }

                        navigateTo(DETAIL_ITEM_SCREEN)

                    }


                }

                HEART_RATE ->{
                    launchCatching {
                        if (heartRateList.isNotEmpty()){
                            val newDate = mutableListOf<String>()
                            val newTimeAndValue = mutableListOf<Map<String, String>>()
                            val distinct = heartRateList.distinctBy { it.heartRateDate }
                            val distinctDate = distinct.sortedWith(
                                compareBy({it.heartRateDate.toDate()},{it.heartRateTime.toTime()})
                            )
                            val indices = distinctDate.indices.toList()
                            val sortedHeartRateList = heartRateList.sortedWith(
                                compareBy({it.heartRateDate.toDate()},
                                    {it.heartRateTime.toTime()})
                            )

                            distinctDate.forEach { distinctHrItem->
                                newDate.add(distinctHrItem.heartRateDate)
                                val newMap = mutableMapOf<String, String>()
                                sortedHeartRateList.forEach {hrItemFromFlow ->
                                    if (distinctHrItem.heartRateDate == hrItemFromFlow.heartRateDate ){
                                        newMap[hrItemFromFlow.heartRateTime] = hrItemFromFlow.heartRate
                                    }

                                }
                                newTimeAndValue.add(newMap)

                            }
                            _state.value = _state.value.copy(
                                dateList = newDate,
                                timeAndValue = newTimeAndValue,
                                currentTimeAndValue = newTimeAndValue.last(),
                                displayDate = newDate.last(),
                                displayValue = newTimeAndValue.last()[newTimeAndValue.last().keys.last()].toString(),
                                indices = indices,

                                )
                        }

                        navigateTo(DETAIL_ITEM_SCREEN)

                    }


                }

                BODY_TEMP ->{

                    launchCatching {
                        if (bodyTempList.isNotEmpty()){
                            val newDate = mutableListOf<String>()
                            val newTimeAndValue = mutableListOf<Map<String, String>>()

                            val distinct = bodyTempList.distinctBy { it.bodyTempDate }
                            val distinctDate = distinct.sortedWith(
                                compareBy({it.bodyTempDate.toDate()},
                                    {it.bodyTempTime.toTime()})
                            )

                            val indices = distinctDate.indices.toList()

                            val sortedBodyTempList = bodyTempList.sortedWith(
                                compareBy({it.bodyTempDate.toDate()},
                                    {it.bodyTempTime.toTime()})
                            )


                            distinctDate.forEach { distinctBtItem->
                                newDate.add(distinctBtItem.bodyTempDate)
                                val newMap = mutableMapOf<String, String>()
                                sortedBodyTempList.forEach {btItemFromFlow ->
                                    if (distinctBtItem.bodyTempDate == btItemFromFlow.bodyTempDate ){
                                        newMap[btItemFromFlow.bodyTempTime] = btItemFromFlow.bodyTemp
                                    }

                                }
                                newTimeAndValue.add(newMap)

                            }
                            _state.value = _state.value.copy(
                                dateList = newDate,
                                timeAndValue = newTimeAndValue,
                                currentTimeAndValue = newTimeAndValue.last(),
                                displayDate = newDate.last(),
                                displayValue = newTimeAndValue.last()[newTimeAndValue.last().keys.last()].toString(),
                                indices = indices,

                                )
                        }

                        navigateTo(DETAIL_ITEM_SCREEN)

                    }


                }

            }

        }
    }



    fun menuIconClick(navigateToSettings: (String) -> Unit) {
        launchCatching {
            navigateToSettings(SETTINGS_SCREEN)
        }
    }

    private fun predictRiskLevel(
        age:String, bpSystolic:String, bpDiastolic:String,
        bloodSugar:String, bodyTemp:String, heartRate:String
    ){
        launchCatching {
            val response = webService.predictRiskLevel(
                age, bpSystolic, bpDiastolic, bloodSugar, bodyTemp, heartRate
            )
            if (response.isSuccessful){
                val res = response.body() as Map<*, *>


                val predictionResult = res["result"]
                //Log.d("Response from server", predictionResult.toString())
                val userInfoUpdate = mapOf("result" to  predictionResult.toString())
                storageService.updateUserInfo(userId,userInfoUpdate,)


            }else{

            }
        }
    }

    fun onLeftArrowClick(currentDate: String){
        val currentDateIndex = state.value.dateList.indexOf(currentDate)
        if (currentDateIndex <= state.value.indices.size - 1 && currentDateIndex > -1){
            if (currentDateIndex > 0){
                val newIndex =  currentDateIndex - 1
                val newTimeValueMap = state.value.timeAndValue[newIndex]
                val newTimeMapLastKey = newTimeValueMap.keys.toList().last()
                val newLastWeight = newTimeValueMap[newTimeMapLastKey]
                _state.value = _state.value.copy(
                    displayDate = state.value.dateList[newIndex],
                    currentTimeAndValue = newTimeValueMap as MutableMap<String, String>,
                    displayValue = newLastWeight!!
                )
            }
        }

    }

    fun onRightArrowClick(currentDate: String){
        val currentDateIndex = state.value.dateList.indexOf(currentDate)
        if (currentDateIndex < state.value.dateList.size - 1 && currentDateIndex >= 0){
            val newIndex = currentDateIndex + 1
            val newTimeValueMap = state.value.timeAndValue[newIndex]
            val newTimeMapLastKey = newTimeValueMap.keys.toList().last()
            val newLastWeight = newTimeValueMap[newTimeMapLastKey]
            _state.value = _state.value.copy(
                displayDate = _state.value.dateList[newIndex],
                currentTimeAndValue = newTimeValueMap as MutableMap<String, String>,
                displayValue = newLastWeight!!
            )
        }

    }

    fun onTimeItemClick(selectedValue: String){
        _state.value = _state.value.copy(
            displayValue = selectedValue
        )
    }

    fun onBackPressed(openAndPopUp: (String, String,) -> Unit){
        launchCatching {
            openAndPopUp(HOME_SCREEN, WEIGHT_SCREEN,)
        }

    }

    fun onFABClick(onFABClicked: (String,) -> Unit) {
        launchCatching {
            when(state.value.category){
                WEIGHT->{_state.value =
                    _state.value.copy(initialValue1 = state.value.latestWeight)

                    onFABClicked(ADD_ITEM_SCREEN,)
                }

                BLOOD_PRESSURE->{_state.value =
                    _state.value.copy(
                        initialValue1 = state.value.bpSystolic,
                        initialValue2 = state.value.bpDiastolic
                    )

                    onFABClicked(ADD_ITEM_SCREEN,)
                }

                BLOOD_SUGAR->{_state.value =
                    _state.value.copy(initialValue1 = state.value.bloodSugar)

                    onFABClicked(ADD_ITEM_SCREEN,)
                }

                HEART_RATE->{_state.value =
                    _state.value.copy(initialValue1 = state.value.heartRate)

                    onFABClicked(ADD_ITEM_SCREEN,)
                }

                BODY_TEMP->{_state.value =
                    _state.value.copy(initialValue1 = state.value.latestBodyTemp)

                    onFABClicked(ADD_ITEM_SCREEN,)
                }

            }

        }
    }


    fun navigateBackToDoctorScreen(navigateBack: (String, String, String) -> Unit) {
        navigateBack(DOCTOR_HOME_SCREEN, PATIENT_DATA_NAV, currentUser)
    }

    // Add Item Section
    fun onNewWeightValue(newValue: String){
        _state.value = _state.value.copy( initialValue1 = newValue)

    }

    fun onNewBloodPressure(diastolic: String, systolic:String) {
        _state.value = _state.value.copy(
            initialValue2 = diastolic,
            initialValue1 = systolic
        )

    }
    fun onNewBloodSugar(bs: String) {
        _state.value = _state.value.copy(
            initialValue1 = bs
        )

    }

    fun onNewHeartRate(hr: String) {
        _state.value = _state.value.copy(
            initialValue1 = hr
        )
    }

    fun onNewBodyTemp(newBodyTemp: String) {
        _state.value = _state.value.copy(
            initialValue1 = newBodyTemp
        )
    }

    fun onClosedButtonClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            openAndPopUp(DETAIL_ITEM_SCREEN, ADD_ITEM_SCREEN)

        }
    }

    fun saveAndNavigate(
        dateTime: ZonedDateTime,
        openAndPopUp: (String, String) -> Unit
    ) {

        launchCatching {

            when (state.value.category) {
                WEIGHT -> {
                    val ww = Weight(
                        weightTime = Utils.fromDateTimeToTime(dateTime),
                        weightDate = Utils.fromDateTimeToDate(dateTime),
                        weight = state.value.initialValue1
                    )

                    storageService.saveItem(state.value.category, userId, ww)
                    openAndPopUp(HOME_SCREEN, ADD_ITEM_SCREEN)

                }
                BLOOD_PRESSURE -> {
                    val bp = BloodPressure(
                        bloodPressureDate = Utils.fromDateTimeToDate(dateTime),
                        bloodPressureTime = Utils.fromDateTimeToTime(dateTime),
                        diastolic = state.value.initialValue2,
                        systolic = state.value.initialValue1
                    )
                    storageService.saveItem(state.value.category,userId, bp)
                    openAndPopUp(HOME_SCREEN, ADD_ITEM_SCREEN)
                }

                BLOOD_SUGAR -> {
                    val bp = BloodSugar(
                        bloodSugarDate = Utils.fromDateTimeToDate(dateTime),
                        bloodSugarTime = Utils.fromDateTimeToTime(dateTime),
                        bloodSugar = state.value.initialValue1,

                        )
                    storageService.saveItem(state.value.category,userId, bp)
                    openAndPopUp(HOME_SCREEN, ADD_ITEM_SCREEN)
                }

                HEART_RATE -> {
                    val hr = HeartRate(
                        heartRateDate = Utils.fromDateTimeToDate(dateTime),
                        heartRateTime = Utils.fromDateTimeToTime(dateTime),
                        heartRate = state.value.initialValue1,

                        )
                    storageService.saveItem(state.value.category,userId, hr)
                    openAndPopUp(HOME_SCREEN, ADD_ITEM_SCREEN)
                }


                BODY_TEMP -> {
                    val bt = BodyTemp(
                        bodyTempDate = Utils.fromDateTimeToDate(dateTime),
                        bodyTempTime = Utils.fromDateTimeToTime(dateTime),
                        bodyTemp = state.value.initialValue1,

                        )
                    storageService.saveItem(state.value.category,userId, bt)
                    openAndPopUp(HOME_SCREEN, ADD_ITEM_SCREEN)
                }
            }

        }

    }

    companion object{
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
    }


}


