package com.hopeinyang.preg_health.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hopeinyang.preg_health.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetPickerLayout(
    showBottomSheet: Boolean,
    firstValues: Pair<Int,Int>,
    lastValues: Pair<Int, Int>,
    valueSeparator: String,
    @StringRes pickerTitle: Int,
    onResetBottomSheet:() -> Unit,
    onSaveButtonClicked: () -> Unit,
    pickerValueState: PickerState,
    pickerUnitState: PickerState


){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()



    if (showBottomSheet){
        ModalBottomSheet(onDismissRequest = onResetBottomSheet,
            sheetState = sheetState

        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(8.dp)
            ) {
                PickerExample(
                    pickerValueState,
                    pickerUnitState,
                    firstValues = firstValues,
                    lastValues = lastValues,
                    valueSeparator = valueSeparator,
                    pickerTitle = pickerTitle,
                    onCancelButtonClicked = onResetBottomSheet,
                    onSaveButtonClicked = onSaveButtonClicked,


                )



            }
        }
    }

}


