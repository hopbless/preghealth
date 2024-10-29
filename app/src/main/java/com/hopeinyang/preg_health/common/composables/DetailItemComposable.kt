package com.hopeinyang.preg_health.common.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hopeinyang.preg_health.common.ext.spacer
import com.hopeinyang.preg_health.common.ext.toTime
import com.hopeinyang.preg_health.components.BMICard


@Composable
 fun DetailItems(
    showDetailCard:Boolean = false,
    displayItemValue: String,
    height:Float,
    @StringRes displayItemUnit: Int,
    itemTimeAndValue: Map<String, String>,
    category: String,
    onItemClicked: (String) -> Unit
){

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    )
    {

        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 30.dp),


            ) {
            Text(text = displayItemValue, modifier = Modifier,
                fontSize = 60.sp

            )
            Text(text = stringResource(displayItemUnit), modifier = Modifier
                .paddingFromBaseline(top = 55.dp, bottom = 0.dp)
            )

        }

        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.Center,

            ) {

            ItemTimeAndValueRowItems(
                currentValue = displayItemValue,
                itemTimeAndValue = itemTimeAndValue,
                onItemClick = onItemClicked ,

                )


        }

        Spacer(modifier = Modifier
            .spacer()
            .height(4.dp)
        )

        when (category){
            "Weight" ->{
                BMICard(displayItemValue, height)

            }

        }

    }

}

@Composable
 fun ItemTimeAndValueRowItems(
    currentValue: String,
    itemTimeAndValue: Map<String, String>,
    onItemClick: (String) -> Unit,

    ){

    Column {

        LazyRow(
            state = rememberLazyListState()
        ) {
            val itemTimeAndValueSorted = itemTimeAndValue.keys.toList().sortedBy { it.toTime() }
            items(itemTimeAndValueSorted) { itemValue ->
                ItemTimeView(
                    timeValue = itemValue,
                    currentItemValue = currentValue,
                    itemTimeAndValue = itemTimeAndValue,
                    onItemClick = onItemClick
                )

            }
        }
    }

}

@Composable
private fun ItemTimeView(
    timeValue: String,
    currentItemValue: String,
    itemTimeAndValue: Map<String, String>,
    onItemClick: (String) -> Unit,

    ){
    val itemValue = itemTimeAndValue[timeValue]
    val selected = currentItemValue == itemValue
    Text(
        text = timeValue,
        modifier = Modifier
            .clickable { onItemClick.invoke(itemValue!!) }
            .background(if (selected) Color.Green else Color.Transparent)
            .padding(horizontal = 8.dp)
    )

}