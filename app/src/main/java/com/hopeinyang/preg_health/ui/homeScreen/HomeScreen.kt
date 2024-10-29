package com.hopeinyang.preg_health.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.components.PreHealthTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navigateTo: (String) -> Unit,
    navigateToSettings: (String) -> Unit,
    navigateBack: (String, String, String) -> Unit
){


    val uiState by viewModel.state.collectAsStateWithLifecycle()


    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
    modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = { PreHealthTopAppBar(
        scrollBehavior =scrollBehavior,
        isDoctorAccount = uiState.doctorAccount,
        navigateToSettings = {viewModel.menuIconClick(navigateToSettings)},
        navigateBack = {viewModel.navigateBackToDoctorScreen(navigateBack)}
    )
    }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            CardItem(
                unit_text = R.string.blood_pressure_unit,
                drawable1 = R.drawable.blood_pressure_fill1_wght400_grad0_opsz24,
                value = "${uiState.bpSystolic}/${uiState.bpDiastolic}",
                date = uiState.bpDate,
                cardTitle = R.string.blood_pressure,
                result = uiState.bpResultAndColor.keys.last(),
                iconAndResultColor = Pair(
                    Color(130, 153, 102),
                    uiState.bpResultAndColor.entries.last().value
                ),
                textStyle = MaterialTheme.typography.displayMedium,
            ){viewModel.onCardClick("BloodPressure", navigateTo)}

            Spacer(modifier = Modifier.height(8.dp))

            CardItem(
                unit_text = R.string.blood_sugar_unit,
                drawable1 = R.drawable.glucose_fill0_wght400_grad0_opsz24,
                value = uiState.bloodSugar,
                date = uiState.bsDate,
                cardTitle = R.string.blood_sugar,
                result = uiState.bsResultAndColor.keys.last(),
                iconAndResultColor = Pair(
                    Color(228, 27, 189),
                    uiState.bsResultAndColor.entries.last().value
                ),
                textStyle = MaterialTheme.typography.displayLarge,
            ){viewModel.onCardClick("BloodSugar", navigateTo)}

            Spacer(modifier = Modifier.height(8.dp))

            CardItem(
                unit_text = R.string.heart_rate_unit,
                drawable1 = R.drawable.health_metrics_asset,
                value = uiState.heartRate,
                date = uiState.hrDate,
                cardTitle = R.string.heart_rate,
                result = uiState.hrResultAndColor.keys.last(),
                iconAndResultColor = Pair(
                    Color(241, 14, 85),
                    uiState.hrResultAndColor.entries.last().value
                ),
                textStyle = MaterialTheme.typography.displayLarge,
            ){viewModel.onCardClick("HeartRate", navigateTo)}

            Spacer(modifier = Modifier.height(8.dp))

            CardItem(
                unit_text = R.string.body_temp_unit,
                drawable1 = R.drawable.temp_asset,
                value = uiState.latestBodyTemp,
                date = uiState.btDate,
                cardTitle = R.string.bodyTemp,
                result = uiState.btResultAndColor.keys.last(),
                iconAndResultColor = Pair(
                    Color(150, 88, 167),
                    uiState.btResultAndColor.entries.last().value
                ),
                textStyle = MaterialTheme.typography.displayLarge,
            ){viewModel.onCardClick("BodyTemp", navigateTo)}

            Spacer(modifier = Modifier.height(8.dp))


            CardItem(
                unit_text = R.string.weight_unit,
                drawable1 = R.drawable.fitness_center_fill1_wght400_grad0_opsz24,
                value = uiState.latestWeight,
                date = uiState.weightDate,
                cardTitle = R.string.weight,
                result = uiState.weightResultAndColor.keys.last(),
                iconAndResultColor = Pair(
                    Color(13, 231, 242),
                    uiState.weightResultAndColor.entries.last().value
                ),
                textStyle = MaterialTheme.typography.displayLarge,
            ){viewModel.onCardClick("Weight",navigateTo)}

            Spacer(modifier = Modifier.height(8.dp))

            CardItem(
                unit_text = R.string.prediction_result_unit,
                drawable1 = R.drawable.health_metrics_asset,
                value = stringResource(id = R.string.prediction_result),
                date = uiState.hrDate,
                cardTitle = R.string.prediction,
                result = uiState.predictionResult.keys.last(),
                iconAndResultColor = Pair(
                    Color(19, 236, 193),
                    uiState.predictionResult.entries.last().value
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
            ){}
            Spacer(modifier = Modifier.height(4.dp))

           





        }

    }
}
