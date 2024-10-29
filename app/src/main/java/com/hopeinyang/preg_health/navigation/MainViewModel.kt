package com.hopeinyang.preg_health.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hopeinyang.preg_health.common.snackbar.SnackbarManager
import com.hopeinyang.preg_health.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.hopeinyang.preg_health.data.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
    private val logService: LogService,
) : ViewModel()  {



    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )






}




