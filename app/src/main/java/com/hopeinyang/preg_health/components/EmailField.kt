package com.hopeinyang.preg_health.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmailField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        singleLine = true,
        value = value,
        onValueChange = {onNewValue(it)},
        placeholder = { Text("Enter Email Address") },
        leadingIcon = {}
    )
}