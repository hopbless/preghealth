package com.hopeinyang.preg_health.components

import androidx.compose.runtime.*

@Composable
fun rememberPickerState() = remember { PickerState() }

class PickerState {
    var selectedItem by mutableStateOf("")
}