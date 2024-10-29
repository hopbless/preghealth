package com.hopeinyang.preg_health.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.common.composables.DetailItems
import com.hopeinyang.preg_health.components.DetailScreenDateSelection
import com.maxkeppeker.sheets.core.models.base.UseCaseState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailItemScreen(
    viewModel: HomeScreenViewModel,
    onFABClicked: (String,) -> Unit,
    openAndPopUp: (String, String) -> Unit,
){
    val uiState by viewModel.state.collectAsStateWithLifecycle()


    var isClicked by remember { mutableStateOf(false) }
    var onResetSheet: UseCaseState.() -> Unit = {isClicked = false}


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),

            topBar = { CenterAlignedTopAppBar(
                modifier = Modifier,
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    scrolledContainerColor = MaterialTheme.colorScheme.background

                ),
                title = { Text(text = uiState.category) },
                navigationIcon = {

                    IconButton(onClick = { viewModel.onBackPressed(openAndPopUp) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )

                    }

                },
                actions = {

//                    IconButton(onClick = {
//
//
//                    }) {
//                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
//                    }

                }
            )
            },
            floatingActionButton = {
                if (!uiState.doctorAccount){
                    FloatingActionButton(onClick = { viewModel.onFABClick(onFABClicked) }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)

                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End


        ) {contentPadding ->
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .alpha(if (isClicked) 0f else 1f)

            ) {

                when(uiState.category){
                    "Weight" ->{
                        if (uiState.dateList.isNotEmpty()){
                            DetailScreenDateSelection(
                                displayDate = uiState.displayDate,
                                isClicked = isClicked,
                                onDateClicked = {isClicked = true},
                                onResetSheet = onResetSheet,
                                onLeftArrowClick = viewModel ::onLeftArrowClick,
                                onRightArrowClick = viewModel ::onRightArrowClick
                            )

                            DetailItems(
                                showDetailCard = true,
                                displayItemValue = uiState.displayValue,
                                displayItemUnit = R.string.weight_unit,
                                height = uiState.height,
                                itemTimeAndValue = uiState.currentTimeAndValue,
                                onItemClicked = viewModel :: onTimeItemClick,
                                category = uiState.category
                            )


                        }else{
                            NoData(modifier = Modifier)
                        }

                    }
                    "BloodPressure"->{
                        if (uiState.dateList.isNotEmpty()){
                            DetailScreenDateSelection(
                                displayDate = uiState.displayDate,
                                isClicked = isClicked,
                                onDateClicked = {isClicked = true},
                                onResetSheet = onResetSheet,
                                onLeftArrowClick = viewModel ::onLeftArrowClick,
                                onRightArrowClick = viewModel ::onRightArrowClick
                            )

                            DetailItems(
                                showDetailCard = true,
                                displayItemValue = uiState.displayValue,
                                displayItemUnit = R.string.blood_pressure_unit,
                                height = uiState.height,
                                itemTimeAndValue = uiState.currentTimeAndValue,
                                onItemClicked = viewModel :: onTimeItemClick,
                                category = uiState.category
                            )

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                thickness = 1.dp
                            )


                        }else{
                            NoData(modifier = Modifier)
                        }

                    }
                    "BloodSugar"->{
                        if (uiState.dateList.isNotEmpty()){
                            DetailScreenDateSelection(
                                displayDate = uiState.displayDate,
                                isClicked = isClicked,
                                onDateClicked = {isClicked = true},
                                onResetSheet = onResetSheet,
                                onLeftArrowClick = viewModel ::onLeftArrowClick,
                                onRightArrowClick = viewModel ::onRightArrowClick
                            )

                            DetailItems(
                                showDetailCard = true,
                                displayItemValue = uiState.displayValue,
                                displayItemUnit = R.string.blood_sugar_unit,
                                height = uiState.height,
                                itemTimeAndValue = uiState.currentTimeAndValue,
                                onItemClicked = viewModel :: onTimeItemClick,
                                category = uiState.category
                            )

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                thickness = 1.dp
                            )


                        }else{
                            NoData(modifier = Modifier)
                        }
                    }
                    "HeartRate"->{
                        if (uiState.dateList.isNotEmpty()){
                            DetailScreenDateSelection(
                                displayDate = uiState.displayDate,
                                isClicked = isClicked,
                                onDateClicked = {isClicked = true},
                                onResetSheet = onResetSheet,
                                onLeftArrowClick = viewModel ::onLeftArrowClick,
                                onRightArrowClick = viewModel ::onRightArrowClick
                            )

                            DetailItems(
                                showDetailCard = true,
                                displayItemValue = uiState.displayValue,
                                displayItemUnit = R.string.heart_rate_unit,
                                height = uiState.height,
                                itemTimeAndValue = uiState.currentTimeAndValue,
                                onItemClicked = viewModel :: onTimeItemClick,
                                category = uiState.category
                            )

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                thickness = 1.dp
                            )


                        }else{
                            NoData(modifier = Modifier)
                        }
                    }
                    "BodyTemp"->{
                        if (uiState.dateList.isNotEmpty()){
                            DetailScreenDateSelection(
                                displayDate = uiState.displayDate,
                                isClicked = isClicked,
                                onDateClicked = {isClicked = true},
                                onResetSheet = onResetSheet,
                                onLeftArrowClick = viewModel ::onLeftArrowClick,
                                onRightArrowClick = viewModel ::onRightArrowClick
                            )

                            DetailItems(
                                showDetailCard = true,
                                displayItemValue = uiState.displayValue,
                                displayItemUnit = R.string.body_temp_unit,
                                height = uiState.height,
                                itemTimeAndValue = uiState.currentTimeAndValue,
                                onItemClicked = viewModel :: onTimeItemClick,
                                category = uiState.category
                            )

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                thickness = 1.dp
                            )

                        }else{
                            NoData(modifier = Modifier)
                        }
                    }

                }



            }

        }
    }
}



@Composable
private fun NoData(modifier: Modifier){
    Text(text = "No Data to Display", modifier
        .padding(vertical = 40.dp, horizontal = 16.dp)
        .fillMaxWidth()
    )
}