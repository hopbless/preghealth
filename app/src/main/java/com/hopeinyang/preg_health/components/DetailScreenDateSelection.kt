package com.hopeinyang.preg_health.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.ArrowDropDown

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hopeinyang.preg_health.R

import com.maxkeppeker.sheets.core.models.base.UseCaseState


@Composable
fun DetailScreenDateSelection(
    displayDate: String,
    isClicked: Boolean,
    onDateClicked: () -> Unit,
    onResetSheet: UseCaseState.() -> Unit,
    onLeftArrowClick: (String) -> Unit,
    onRightArrowClick: (String) -> Unit
){

    Row(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 20.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = {onLeftArrowClick(displayDate)}) {
            Icon(painter = painterResource(
                id = R.drawable.arrow_back_ios_fill0_wght400_grad0_opsz24),
                contentDescription = null,
                //modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
            )

        }

        Row(modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(text = displayDate)
            IconButton(onClick = onDateClicked, modifier = Modifier.size(24.dp)) {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null )

                if (isClicked) {
                    MyDatePicker(closeSelection = onResetSheet, onDateSelected = { })

                }

            }

        }

        IconButton(onClick = {onRightArrowClick(displayDate)}) {
            Icon(painter = painterResource(
                id = R.drawable.arrow_forward_ios_fill0_wght400_grad0_opsz24),
                contentDescription = null,
                //modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
            )

        }
    }
}