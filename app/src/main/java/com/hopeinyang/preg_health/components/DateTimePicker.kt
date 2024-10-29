package com.hopeinyang.preg_health.components


import androidx.compose.runtime.*
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime


@Composable
internal fun DateTimePicker(
    closedSelection: UseCaseState.() -> Unit,
    onSelectedDateTime: (ZonedDateTime) -> Unit
){
    val selectedDateTime = remember { mutableStateOf<ZonedDateTime?>(
        ZonedDateTime.now()
    )}

    DateTimeDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = closedSelection),
        selection = DateTimeSelection.DateTime(
           selectedDate = selectedDateTime.value!!.toLocalDate(),
            selectedTime = selectedDateTime.value!!.toLocalTime()
        ) {localDateTime ->
            localDateTime.apply {
                this.atZone(ZoneId.systemDefault()).apply(onSelectedDateTime)
            }
        }
    )
}

