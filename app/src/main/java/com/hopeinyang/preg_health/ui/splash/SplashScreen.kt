package com.hopeinyang.preg_health.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.common.composables.BasicButton
import com.hopeinyang.preg_health.common.ext.basicButton
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 1000L

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    openAndPopUp: (String, String, String) -> Unit,
    navigateToLogin: (String, String) -> Unit


) {
    val uiState by viewModel.uiState
    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.showError) {
            Text(text = stringResource(R.string.generic_error))


            BasicButton(R.string.try_again, Modifier.basicButton()) { viewModel.onAppStart(
                openAndPopUp,  navigateToLogin)}
        } else {

            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            //BasicButton(R.string.continue_text, Modifier.basicButton()) { viewModel.onContinueButtonClick()}

        }
    }


        LaunchedEffect(true) {
            delay(SPLASH_TIMEOUT)
            viewModel.onAppStart(openAndPopUp,  navigateToLogin)
        }




}