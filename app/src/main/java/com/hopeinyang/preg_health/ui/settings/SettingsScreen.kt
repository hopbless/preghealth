package com.hopeinyang.preg_health.ui.settings

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.common.composables.*
import com.hopeinyang.preg_health.common.ext.card
import com.hopeinyang.preg_health.common.ext.spacer
import com.hopeinyang.preg_health.data.dto.UserInfo


@Composable
fun SettingsScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    openAndNavigate: (String, String) -> Unit,

) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BasicToolbar(R.string.settings)

        Spacer(modifier = Modifier.spacer())

        if (uiState.userId.isEmpty()) {
            RegularCardEditor(R.string.sign_in, R.drawable.lock, "Sign In", Modifier.card()) {
                viewModel.onLoginClick(openScreen)
            }

            RegularCardEditor(R.string.create_account, R.drawable.lock, "Create Account", Modifier.card()) {
                viewModel.onSignUpClick(openScreen)
            }
        } else if (uiState.isDoctorAccount && uiState.userId.isNotEmpty()) {

            DoctorSetting(uiState = uiState,
                modifier = Modifier,
                viewModel = viewModel,
                openAndNavigate = openAndNavigate
            )


            SignOutCard { viewModel.onSignOutClick(restartApp) }
            DeleteMyAccountCard { viewModel.onDeleteMyAccountClick(restartApp) }
        }else{

            PatientSetting(
                uiState = uiState,
                modifier = modifier,
                viewModel = viewModel,
                openAndNavigate = openAndNavigate,
                onDoctorSelected = {isSelected, doctorUID -> viewModel.onDoctorSelected(isSelected, doctorUID)}
            )

            SignOutCard { viewModel.onSignOutClick(restartApp) }
            DeleteMyAccountCard { viewModel.onDeleteMyAccountClick(restartApp) }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PatientSetting(
    uiState:SettingsUiState, modifier: Modifier,
    viewModel:SettingsViewModel,
    openAndNavigate: (String, String) -> Unit,
    onDoctorSelected: (Boolean, String) -> Unit


){
    Column(
        modifier = modifier
            //.fillMaxWidth()
            //.fillMaxHeight()
            //.verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        MyTextFieldComponent(
            value = uiState.firstName,
            painter = painterResource(id = R.drawable.person_3_fill),
            onTextChanged = {viewModel.onFirstNameChange(it)},
            text = R.string.first_name,
            modifier = Modifier.weight(1f)
        )

        MyTextFieldComponent(
            value = uiState.lastName,
            painter = painterResource(id = R.drawable.person_3_fill),
            onTextChanged = { viewModel.onLastNameChange(it)},
            text = R.string.last_name,
            modifier = Modifier.weight(1f)
        )

    }
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()

    ) {

        DecimalNumberComponent(
            value = uiState.height,
            painter = painterResource(id = R.drawable.height_fill0_wght400_grad0_opsz24),
            onTextChanged = {viewModel.onHeightChange(it)},
            label = R.string.height,
            modifier = Modifier.weight(1f)
        )

        IntegerNumberComponent(
            value = uiState.age,
            painter = painterResource(id = R.drawable.edit_asset),
            onTextChanged = {viewModel.onAgeChange(it)},
            label = R.string.age,
            modifier = Modifier.weight(1f)
        )

    }

    Spacer(modifier = Modifier.height(8.dp))

    MyTextFieldComponent(
        value = uiState.address,
        painter = painterResource(id = R.drawable.home_asset),
        onTextChanged = {viewModel.onAddressChange(it)},
        text = R.string.address,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    PhoneNumberComponent(
        value = uiState.phoneNumber,
        painter = painterResource(id = R.drawable.call_asset),
        onTextChanged = {viewModel.onPhoneNumberChange(it)},
        label = R.string.phone_number,
        modifier = Modifier.fillMaxWidth()

    )

    Spacer(modifier = Modifier
        .spacer()
        .height(20.dp))

    
    if (uiState.isDoctorSelected){
        NormalTextComponent(value = stringResource(id = R.string.selected_doctor))
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ){
            val selectedDoctor = uiState.doctorsList.first{it.doctorUID==uiState.doctorUID}
            val fName = selectedDoctor.firstName ?: " "
            val lName = selectedDoctor.lastName ?: " "
            val hName = selectedDoctor.hospitalName ?: " "

            Text(text = "Doctor's Name: $fName $lName")
            Text(text = "Hospital Name: $hName")
        }



    }else{
        NormalTextComponent(value = stringResource(id = R.string.available_doctors))

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .heightIn(max = 200.dp)
                .fillMaxWidth()

        ){
            stickyHeader {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)

                ) {
                    Text(
                        text = "Doctor's Name",
                        fontWeight = FontWeight.Bold,

                    )
                    Text(
                        text = "Hospital",
                        fontWeight = FontWeight.Bold,

                    )
                    Text(
                        text = "Address",
                        fontWeight = FontWeight.Bold,

                    )
                }
            }
            items(uiState.doctorsList.size){doctor->

                DoctorView(
                    uiState.doctorsList[doctor], onDoctorSelected
                )


            }
        }

        Spacer(modifier = Modifier
            .spacer()
            .height(10.dp))
    }

    BasicButton(
        text = R.string.update,
        modifier = Modifier
    ) {
        viewModel.patientUpdateButtonClick(openAndNavigate)
    }
}

@Composable
private fun DoctorView(doctor:UserInfo,
                       onDoctorSelected: (Boolean, String) -> Unit
){


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)

    ) {
        Row (horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {

                   onDoctorSelected(true, doctor.userId!!)
                }
                .fillMaxWidth()
                .heightIn(max = 50.dp)
        )
        {
            Text(
                text = "${doctor.firstName!!} ${doctor.lastName}",

            )
            Text(text = doctor.hospitalName)
            Text(text = doctor.address!!)

        }
    }



}

@Composable
private fun DoctorSetting(
    uiState:SettingsUiState, modifier: Modifier,
    viewModel:SettingsViewModel,
    openAndNavigate:(String, String) -> Unit

){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            //.verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Personal Details",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.outline
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            MyTextFieldComponent(
                value = uiState.firstName,
                painter = painterResource(id = R.drawable.person_3_fill),
                onTextChanged = {viewModel.onFirstNameChange(it)},
                text = R.string.first_name,
                modifier = Modifier.weight(1f)
            )

            MyTextFieldComponent(
                value = uiState.lastName,
                painter = painterResource(id = R.drawable.person_3_fill),
                onTextChanged = { viewModel.onLastNameChange(it)},
                text = R.string.last_name,
                modifier = Modifier.weight(1f)
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        MyTextFieldComponent(
            value = uiState.hospitalName,
            painter = painterResource(id = R.drawable.local_hospital_asset),
            onTextChanged = {viewModel.onAddressChange(it)},
            text = R.string.address,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        MyTextFieldComponent(
            value = uiState.address,
            painter = painterResource(id = R.drawable.home_asset),
            onTextChanged = {viewModel.onAddressChange(it)},
            text = R.string.address,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        PhoneNumberComponent(
            value = uiState.phoneNumber,
            painter = painterResource(id = R.drawable.call_asset),
            onTextChanged = {viewModel.onPhoneNumberChange(it)},
            label = R.string.phone_number,
            modifier = Modifier.fillMaxWidth()

        )


        Spacer(modifier = Modifier
            .spacer()
            .height(20.dp))

        BasicButton(
            text = R.string.update,
            modifier = Modifier
        ) {
            viewModel.doctorUpdateButtonClick(openAndNavigate)
        }
    }

}


@Composable
private fun SignOutCard(signOut: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    RegularCardEditor(R.string.sign_out, R.drawable.lock, "Sign Out", Modifier.card()) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.sign_out_title)) },
            text = { Text(stringResource(R.string.sign_out_description)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.sign_out) {
                    signOut()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}


@Composable
private fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    DangerousCardEditor(
        R.string.delete_my_account,
        R.drawable.lock,
        "Delete Account",
        Modifier.card()
    ) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.delete_account_title)) },
            text = { Text(stringResource(R.string.delete_account_description)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.delete_my_account) {
                    deleteMyAccount()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}