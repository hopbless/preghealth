package com.hopeinyang.preg_health


import LoginScreen
import SignUpScreen
import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.*

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.hopeinyang.preg_health.common.snackbar.SnackbarManager
import com.hopeinyang.preg_health.components.PreHealthScreen
import com.hopeinyang.preg_health.ui.addItem.AddItemScreen
import com.hopeinyang.preg_health.ui.homeScreen.HomeScreen

import com.hopeinyang.preg_health.ui.doctorScreen.DoctorScreen
import com.hopeinyang.preg_health.ui.homeScreen.DetailItemScreen
import com.hopeinyang.preg_health.ui.homeScreen.HomeScreenViewModel
import com.hopeinyang.preg_health.ui.settings.SettingsScreen
import com.hopeinyang.preg_health.ui.splash.SplashScreen
import com.hopeinyang.preg_health.ui.theme.PreGHealthTheme

import kotlinx.coroutines.CoroutineScope

@Composable
fun PreHealthApp(){
    PreGHealthTheme() {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            val appState = rememberAppState()
            //val snackbarHostState = remember { SnackbarHostState() }
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackbarHostState.value,
                        modifier = Modifier.padding(8.dp),
                        snackbar = {snackbarData ->
                            Snackbar(
                                snackbarData,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        }

                    )

            }) {innerPadding ->
                NavHost(navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPadding)
                    ){
                    preHealthGraph(appState)
                }

            }
            
        }

    }
}

@Composable
fun rememberAppState(
    snackBarHostState: SnackbarHostState = remember{SnackbarHostState()},
    navController: NavHostController = rememberNavController(),
    snackBarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()

) = remember(snackBarHostState, navController, snackBarManager, resources, coroutineScope ){
    PreHealthAppState(snackBarHostState, navController,snackBarManager,resources, coroutineScope)

}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

fun NavGraphBuilder.preHealthGraph(appState: PreHealthAppState){

    composable(route = SPLASH_SCREEN){
        SplashScreen(
            openAndPopUp = { route, popUp, userId ->
            appState.navigateAndPopUp("$route/$userId", popUp) },
            navigateToLogin = {route, popUp -> appState.navigateAndPopUp(route, popUp)}
        )
    }

    composable(
        route = "$DOCTOR_HOME_SCREEN/{userId}",
        arguments = listOf(
            navArgument("userId"){
                type = NavType.StringType
                nullable = true
            }
        )
    ){
        val param = it.arguments?.get("userId")
        DoctorScreen(
            navigateTo = { userId, route -> appState.navigate("$route/$userId") },
            navigateToSettings = {route, -> appState.navigate("$route/$param")}

        )

    }

    navigation(
        route = "$PATIENT_DATA_NAV/{userId}",
        startDestination = HOME_SCREEN,
        arguments = listOf(
            navArgument("userId"){
                type = NavType.StringType
                nullable = true
            }
        )


    ){

        composable(route = HOME_SCREEN,){
            val param = it.arguments?.getString("userId")
            val viewModel = it.sharedViewModel<HomeScreenViewModel>(navController = appState.navController)
            HomeScreen (
                viewModel = viewModel,
                navigateTo = {route, ->  appState.navigate(route)},
                navigateToSettings = {route -> appState.navigate("$route/$param")},
                navigateBack = {route, popUp, currentUser -> appState.navigateAndPopUp("$route/$currentUser",popUp)}
            )

        }

        composable(route = DETAIL_ITEM_SCREEN,){

            val viewModel = it.sharedViewModel<HomeScreenViewModel>(
                navController = appState.navController)
            DetailItemScreen(
                viewModel = viewModel,
                openAndPopUp = {route, popUp -> appState.navigateAndPopUp(route, popUp)},
                onFABClicked = {route,-> appState.navigate(route)},
            )
        }

        composable(route = ADD_ITEM_SCREEN,){
            val viewModel = it.sharedViewModel<HomeScreenViewModel>(
                navController = appState.navController)
            AddItemScreen(
                viewModel = viewModel,
                openAndPopUp = {route, popUp -> appState.navigateAndPopUp(route,popUp)},

            )

        }


    }




    
    composable(
        route = "$SETTINGS_SCREEN/{userId}",
        arguments = listOf(
            navArgument("userId"){
                type = NavType.StringType
                nullable = true
            }
        )
    ){
        val param = it.arguments?.getString("userId")
        SettingsScreen(
            restartApp = {route -> appState.navigate(route)},
            openScreen ={route -> appState.navigate(route)},
            openAndNavigate = {route, popUp,-> appState.navigateAndPopUp("$route/$param", popUp) },

        )
    }

    composable(
        route = "$SIGN_UP_SCREEN/{userId}",
        arguments = listOf(
            navArgument("userId"){
                type = NavType.StringType
                nullable = true
            }
        )

    ){
        val param = it.arguments?.getString("userId")
        SignUpScreen(
            openAndNavigate = {route, popUp, userId ->
                appState.navigateAndPopUp("$route/$userId",popUp)},
            navigateToLogin = {route, popUp -> appState.navigateAndPopUp(route, popUp)}

        )
    }

    composable(route = LOGIN_SCREEN){
        LoginScreen(openAndPopUp = {route, popUp, userId -> appState.navigateAndPopUp("$route/$userId", popUp)} )
    }

}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController) : T{
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)

}