package com.hopeinyang.preg_health.ui.addItem


import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.components.BottomSheetPickerLayout
import com.hopeinyang.preg_health.components.DateTimePicker
import com.hopeinyang.preg_health.components.PickerState
import com.hopeinyang.preg_health.components.rememberPickerState
import com.hopeinyang.preg_health.ui.homeScreen.HomeScreenViewModel

import com.hopeinyang.preg_health.ui.theme.PreGHealthTheme
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AddItemScreen(
    viewModel: HomeScreenViewModel,
    openAndPopUp: (String, String) -> Unit,
){
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    var dateTime by rememberSaveable { mutableStateOf<ZonedDateTime>(ZonedDateTime.now())}



    var isDateClicked by remember { mutableStateOf(false) }
    var onResetSheet: UseCaseState.() -> Unit = { isDateClicked = false}

    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")

    var showBottomSheet by remember { mutableStateOf(false) }

    var pickerValueState = rememberPickerState()
    var pickerUnitState = rememberPickerState()


    Scaffold(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
    ) {contentPadding ->

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {

                IconButton(
                    onClick = {viewModel.onClosedButtonClick(openAndPopUp)},
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close, contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
                IconButton(onClick = {
                    viewModel.saveAndNavigate(dateTime, openAndPopUp) }) {
                    Icon(
                        imageVector = Icons.Filled.Done, contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }


            }

            Text(
                text = "Enter Manually", fontSize = 40.sp, modifier = Modifier
                    .padding(horizontal = 8.dp)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .fillMaxWidth()
                    .clickable(onClick = { isDateClicked = true }),
                contentAlignment = Alignment.TopEnd



            ) {

                Text(
                    text = "Measurement Time", fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = dateTime.format(dateTimeFormatter), fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()



                )
                IconButton(onClick = { isDateClicked = true }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.arrow_forward_ios_fill0_wght400_grad0_opsz24),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterEnd)
                    )

                }

                if (isDateClicked) {
                    DateTimePicker(onResetSheet) { dateTime = it }

                }
            }



                    when(uiState.category){
                        "Weight"->{
                            ItemValueRow(
                                value = uiState.initialValue1,
                                category = uiState.category,
                                unit = R.string.weight_unit ) {
                                showBottomSheet = true
                            }

                            BottomSheetPicker(
                                firstValues = Pair(1, 300),
                                lastValues = Pair(0, 9),
                                valueSeparator = ".",
                                title = R.string.weight,
                                showBottomSheet = showBottomSheet ,
                                onBottomResetSheet = { showBottomSheet = false },
                                onSaveButtonClose = {
                                    showBottomSheet = false
                                    val newWeight =
                                        "${pickerValueState.selectedItem}.${pickerUnitState.selectedItem}"
                                    viewModel.onNewWeightValue(newWeight)

                                },
                                pickerValueState = pickerValueState,
                                pickerUnitState = pickerUnitState
                            )



                        }
                        "BloodPressure" ->{
                            val bp = "${uiState.initialValue1}/${uiState.initialValue2}"
                            ItemValueRow(
                                value = bp,
                                category = uiState.category,
                                unit = R.string.blood_pressure_unit ) {
                                showBottomSheet = true
                            }

                            BottomSheetPicker(
                                firstValues = Pair(1, 500),
                                lastValues = Pair(1, 200),
                                valueSeparator = "/",
                                title = R.string.blood_pressure,
                                showBottomSheet = showBottomSheet ,
                                onBottomResetSheet = { showBottomSheet = false },
                                onSaveButtonClose = {

                                    showBottomSheet = false
                                    val diastolic = pickerUnitState.selectedItem
                                    val systolic = pickerValueState.selectedItem
                                    viewModel.onNewBloodPressure(diastolic, systolic)

                                },
                                pickerValueState = pickerValueState,
                                pickerUnitState = pickerUnitState
                            )
                        }
                        "BloodSugar" ->{
                            ItemValueRow(
                                value = uiState.initialValue1,
                                category = uiState.category,
                                unit = R.string.blood_sugar_unit ) {
                                showBottomSheet = true
                            }



                            BottomSheetPicker(
                                firstValues = Pair(1, 20),
                                lastValues = Pair(0, 9),
                                valueSeparator = ".",
                                title = R.string.blood_sugar,
                                showBottomSheet = showBottomSheet ,
                                onBottomResetSheet = { showBottomSheet = false },
                                onSaveButtonClose = {
                                    showBottomSheet = false
                                    val bs =
                                        "${pickerValueState.selectedItem}.${pickerUnitState.selectedItem}"
                                    viewModel.onNewBloodSugar(bs)

                                },
                                pickerValueState = pickerValueState,
                                pickerUnitState = pickerUnitState
                            )



                        }
                        "HeartRate" -> {
                            ItemValueRow(
                                value = uiState.initialValue1,
                                category = uiState.category,
                                unit = R.string.heart_rate_unit ) {
                                showBottomSheet = true
                            }

                            BottomSheetPicker(
                                firstValues = Pair(1, 200),
                                lastValues = Pair(0, 9),
                                valueSeparator = ".",
                                title = R.string.heart_rate,
                                showBottomSheet = showBottomSheet ,
                                onBottomResetSheet = { showBottomSheet = false },
                                onSaveButtonClose = {
                                    showBottomSheet = false
                                    val hr = pickerValueState.selectedItem
                                    viewModel.onNewHeartRate(hr)

                                },
                                pickerValueState = pickerValueState,
                                pickerUnitState = pickerUnitState
                            )

                        }
                        "BodyTemp" -> {
                            ItemValueRow(
                                value = uiState.initialValue1,
                                category = uiState.category,
                                unit = R.string.body_temp_unit ) {
                                showBottomSheet = true
                            }



                            BottomSheetPicker(
                                firstValues = Pair(1, 50),
                                lastValues = Pair(0, 9),
                                valueSeparator = ".",
                                title = R.string.bodyTemp,
                                showBottomSheet = showBottomSheet,
                                onBottomResetSheet = { showBottomSheet = false },
                                onSaveButtonClose = {
                                    showBottomSheet = false
                                    val newBodyTemp =
                                        "${pickerValueState.selectedItem}.${pickerUnitState.selectedItem}"
                                    viewModel.onNewBodyTemp(newBodyTemp)

                                },
                                pickerValueState = pickerValueState,
                                pickerUnitState = pickerUnitState
                            )

                        }


                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    thickness = 1.dp
                )





        }
    }
}



@Composable
private fun BottomSheetPicker(
    firstValues: Pair<Int, Int>,
    lastValues: Pair<Int,Int>,
    valueSeparator: String,
    @StringRes title: Int,
    showBottomSheet: Boolean,
    onBottomResetSheet: ()-> Unit,
    onSaveButtonClose: ()-> Unit,
    pickerValueState:PickerState,
    pickerUnitState:PickerState
){

    BottomSheetPickerLayout(
        showBottomSheet = showBottomSheet,
        firstValues = firstValues,
        lastValues = lastValues,
        valueSeparator = valueSeparator,
        pickerTitle = title,
        onResetBottomSheet = onBottomResetSheet,
        onSaveButtonClicked = onSaveButtonClose,
        pickerValueState,
        pickerUnitState
    )

}


@Composable
private fun ItemValueRow(
    value: String,
    category: String,
    @StringRes unit: Int,
    showBottomSheet: () -> Unit,

){
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .fillMaxWidth()
            .clickable(onClick = showBottomSheet),
        verticalAlignment = Alignment.CenterVertically,
        //horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = category,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1.5f)


        )

        Text(
            text = value,

            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(vertical = 20.dp)


        )

        Text(
            text = stringResource(unit),
            textAlign = TextAlign.End,
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(vertical = 20.dp)



        )

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(
                    id = R.drawable.arrow_forward_ios_fill0_wght400_grad0_opsz24),
                contentDescription = null,
                modifier = Modifier

            )

        }
    }
}



@Preview (showBackground = true)
@Composable
private fun WeightMeasurementPreview (){
    PreGHealthTheme {
        //WeightMeasurement(onCheckedMarkClicked = {}, onClosedButtonClicked = {})
    }
}