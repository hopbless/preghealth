package com.hopeinyang.preg_health.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PickerExample(
    pickerValueState: PickerState,
    pickerUnitState: PickerState,
    firstValues: Pair<Int,Int>,
    lastValues: Pair<Int, Int>,
    valueSeparator: String,
    @StringRes pickerTitle: Int,
    onCancelButtonClicked: ()-> Unit,
    onSaveButtonClicked: () -> Unit,

) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            val values = remember { (firstValues.first ..firstValues.second).map { it.toString() } }
            //val valuesPickerState = rememberPickerState()
            val valuesFraction = remember {(lastValues.first .. lastValues.second).map {it.toString()}}
            val units = remember { listOf("seconds", "minutes", "hours") }
            //val unitsPickerState = rememberPickerState()

            Text(text = stringResource(id = pickerTitle), modifier = Modifier.padding(top = 16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Picker(
                    state = pickerValueState,
                    items = values,
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.3f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 32.sp)
                )

                Picker(
                    state = pickerUnitState,
                    items = valuesFraction,
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.7f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 32.sp)
                )
            }

            Text(
                text = "Selected: ${pickerValueState.selectedItem}$valueSeparator${pickerUnitState.selectedItem}",
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(onClick = onCancelButtonClicked,

                ) {
                    Text(text = "Cancel")

                }

                Button(onClick = onSaveButtonClicked,

                ) {
                    Text(text = "Save")

                }
            }


        }
    }
}