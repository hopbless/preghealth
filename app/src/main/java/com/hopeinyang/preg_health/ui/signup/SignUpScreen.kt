import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.common.composables.*
import com.hopeinyang.preg_health.ui.signup.SignUpUiState
import com.hopeinyang.preg_health.ui.signup.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    openAndNavigate: (String, String, String) -> Unit,
    navigateToLogin:(String, String) -> Unit
) {
    val uiState by viewModel.uiState

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        topBar = { CenterAlignedTopAppBar(
            modifier = Modifier,
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                scrolledContainerColor = MaterialTheme.colorScheme.background

            ),
            title = { Text(text = "SignUp") },
            navigationIcon = {



            },
            actions = {

                IconButton(onClick = {


                }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                }

            }
        )
        },
    ){contentPadding ->

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {

            //NormalTextComponent(value = stringResource(id = R.string.hello))
            HeadingTextComponent(value = stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(20.dp))

            Row (modifier = Modifier
                .fillMaxWidth()
                .heightIn(56.dp),
                verticalAlignment = Alignment.CenterVertically,){
                Checkbox(
                    checked = uiState.isDoctorAccount,
                    onCheckedChange = {viewModel.onDoctorAccountSelected(it)}
                )
                Text(text = stringResource(id = R.string.as_doctor))
            }


            if (uiState.isDoctorAccount){
                DoctorSignUpScreen(uiState = uiState, viewModel = viewModel )

            }else{
                PatientSignUpScreen(uiState, viewModel)
            }


            CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions),
                onTextSelected = {
                    /* To Do*/
                },
                onCheckedChange = {
                    viewModel.onCheckBoxChange(it)
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            ButtonComponent(
                value = stringResource(id = R.string.signup),
                onButtonClicked = {
                    viewModel.onSignUpClick(openAndNavigate)
                },
                //isEnabled = signupViewModel.allValidationsPassed.value
            )

            Spacer(modifier = Modifier.height(20.dp))

            DividerTextComponent()

            ClickableLoginTextComponent(tryingToLogin = true) {
                viewModel.loginClick(navigateToLogin)
            }
        }


        if(viewModel.signUpInProgress.value) {
            CircularProgressIndicator()
        }
    }


}

@Composable
fun PatientSignUpScreen(uiState: SignUpUiState, viewModel: SignUpViewModel){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 16.dp)

    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                //.padding(vertical = 8.dp)

        ){
            MyTextFieldComponent(
                value = uiState.firstName,
                painter = painterResource(id = R.drawable.person_3_fill),
                onTextChanged = {viewModel.onFirstNameChange(it)},
                text = R.string.first_name,
                modifier = Modifier.weight(1f)

            )

                Spacer(modifier = Modifier.padding(4.dp))
            

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
            modifier = Modifier
                .fillMaxWidth()
                //.padding(vertical = 8.dp)
        ) {

            DecimalNumberComponent(
                value = uiState.height,
                painter =  painterResource(id = R.drawable.height_fill0_wght400_grad0_opsz24),
                onTextChanged = {viewModel.onHeightChange(it)},
                label = R.string.height,
                modifier = Modifier.weight(1f)
            )


            Spacer(modifier = Modifier.padding(4.dp))

            IntegerNumberComponent(
                value = uiState.age,
                painter = painterResource(id = R.drawable.edit_asset),
                onTextChanged = {viewModel.onAgeChange(it)},
                label = R.string.age,
                modifier = Modifier.weight(1f)
            )


        }

        Spacer(modifier = Modifier.padding(4.dp))

        MyTextFieldComponent(
            value = uiState.address,
            painter = painterResource(id = R.drawable.home_asset),
            onTextChanged = {viewModel.onAddressChange(it)},
            text = R.string.address,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
            //.padding(vertical = 8.dp)
        ) {

            PhoneNumberComponent(
                value = uiState.phoneNumber,
                painter = painterResource(id = R.drawable.call_asset),
                onTextChanged = {viewModel.onPhoneNumberChange(it)},
                label = R.string.phone_number,
                modifier = Modifier.weight(1.0f)

            )


            Spacer(modifier = Modifier.padding(4.dp))

            IntegerNumberComponent(
                value = uiState.weekNo,
                painter = painterResource(id = R.drawable.edit_asset),
                onTextChanged = {viewModel.onPregnancyWeekChange(it)},
                label = R.string.pregnancy_week,
                modifier = Modifier.weight(1.0f)
            )


        }



        Spacer(modifier = Modifier.height(8.dp))

        MyTextFieldComponent(
            value = uiState.email,
            painter = painterResource(id = R.drawable.mail_asset),
            onTextChanged = {viewModel.onEmailChange(it)},
            text = R.string.email,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        PasswordTextFieldComponent(
            value = uiState.password,
            painter = painterResource(id = R.drawable.password_asset),
            onTextSelected = {viewModel.onPasswordChange(it)},
            text = R.string.password
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextFieldComponent(
            value = uiState.repeatPassword,
            painter = painterResource(id = R.drawable.password_asset),
            onTextSelected = {viewModel.onRepeatPasswordChange(it)},
            text = R.string.password
        )

    }


}

@Composable
fun DoctorSignUpScreen(uiState: SignUpUiState, viewModel: SignUpViewModel){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 16.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()

        ){
            MyTextFieldComponent(
                value = uiState.firstName,
                painter = painterResource(id = R.drawable.person_3_fill),
                onTextChanged = {viewModel.onFirstNameChange(it)},
                text = R.string.first_name,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            MyTextFieldComponent(
                value = uiState.lastName,
                painter = painterResource(id = R.drawable.person_3_fill),
                onTextChanged = { viewModel.onLastNameChange(it)},
                text = R.string.last_name,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()

        ){
            MyTextFieldComponent(
                value = uiState.hospitalName,
                painter = painterResource(id = R.drawable.local_hospital_asset),
                onTextChanged = {viewModel.onHospitalNameChange(it)},
                text = R.string.hospital_name,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            MyTextFieldComponent(
                value = uiState.address,
                painter = painterResource(id = R.drawable.home_asset),
                onTextChanged = {viewModel.onAddressChange(it)},
                text = R.string.address,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        MyTextFieldComponent(
            value = uiState.doctorCode,
            painter = painterResource(id = R.drawable.code_asset),
            onTextChanged = {viewModel.onDoctorCodeChange(it)},
            text = R.string.doctor_code,
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

        Spacer(modifier = Modifier.height(8.dp))

        MyTextFieldComponent(
            value = uiState.email,
            painter = painterResource(id = R.drawable.mail_asset),
            onTextChanged = {viewModel.onEmailChange(it)},
            text = R.string.email,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextFieldComponent(
            value = uiState.password,
            painter = painterResource(id = R.drawable.password_asset),
            onTextSelected = {viewModel.onPasswordChange(it)},
            text = R.string.password
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextFieldComponent(
            value = uiState.repeatPassword,
            painter = painterResource(id = R.drawable.password_asset),
            onTextSelected = {viewModel.onRepeatPasswordChange(it)},
            text = R.string.password
        )

    }


}