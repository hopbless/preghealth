
import androidx.compose.foundation.layout.*


import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.common.composables.*
import com.hopeinyang.preg_health.navigation.MainViewModel
import com.hopeinyang.preg_health.ui.login.LoginViewModel
import com.hopeinyang.preg_health.ui.theme.PreGHealthTheme


@Composable
fun LoginScreen(
    openAndPopUp: (String, String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
           color = MaterialTheme.colorScheme.surfaceVariant,

        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp)

            ) {

                NormalTextComponent(value = stringResource(id = R.string.login))
                HeadingTextComponent(value = stringResource(id = R.string.welcome))
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    value = uiState.email,
                    painter = painterResource(id = R.drawable.mail_asset),
                    onTextChanged = viewModel::onEmailChange,
                    text = R.string.email,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                PasswordTextFieldComponent(
                    value = uiState.password,
                    painterResource(id = R.drawable.password_asset),
                    onTextSelected = viewModel::onPasswordChange,
                    text = R.string.password,

                )

                Spacer(modifier = Modifier.height(40.dp))
                UnderLinedTextComponent(
                    value = stringResource(id = R.string.forgot_password),
                    onClick = {viewModel.onForgotPasswordClick()}
                )

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    ){viewModel.onSignInClick(openAndPopUp)
                    }

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                    viewModel.onRegisterClick(it, openAndPopUp)
                })
            }
        }

        if(uiState.isLoginInProgress) {
            CircularProgressIndicator()
        }
    }


//    SystemBackButtonHandler {
//        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
//    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    PreGHealthTheme() {
        //LoginScreen()
    }



}