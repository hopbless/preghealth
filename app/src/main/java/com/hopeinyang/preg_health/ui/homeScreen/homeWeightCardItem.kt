package com.hopeinyang.preg_health.ui.homeScreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.common.composables.HomeScreenCardResult



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    @DrawableRes drawable1:Int,
    @StringRes unit_text: Int,
    @StringRes cardTitle: Int,
    value: String,
    result: String,
    date: String,
    textStyle: TextStyle,
    iconAndResultColor: Pair<Color, Color>,
    onCardItemClicked: () -> Unit,
){

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 16.dp,
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp)),
            onClick = onCardItemClicked
        ) {


            Column(
                horizontalAlignment = Alignment.Start,

                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
                {
                    Text(text = stringResource(id = cardTitle), )

                    HorizontalDivider(
                        thickness = 2.dp, color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()

                    ) {
                        Image(
                            painter = painterResource(drawable1),
                            contentDescription = null,

                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = iconAndResultColor.first)
                        )




                            RowItem(modifier, value, unit_text, textStyle)

                            HomeScreenCardResult(
                                newValue = result,
                                textColor = iconAndResultColor.second,
                                modifier = modifier
                            )




                    }



                       Text(
                            text = date,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

            }
        }
    }

}


@Composable
private fun RowItem(
    modifier: Modifier,
    weightValue: String,
    unit_text: Int,
    textStyle: TextStyle
){
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = weightValue,
            style = textStyle,
            modifier = modifier

        )

        Text(
            text = stringResource(unit_text),
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .paddingFromBaseline(bottom = 0.dp, top = 12.dp)

        )
    }

}

@Composable
private fun NoDataRowItem(modifier: Modifier){
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "No Data",
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier

        )

        Text(
            text = " ",
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .paddingFromBaseline(bottom = 0.dp, top = 12.dp)

        )
    }

}






@Preview(showBackground = true)
@Composable
fun CardItemPreview(){
    CardItem(
        drawable1 = R.drawable.ic_launcher_background,
        onCardItemClicked = {},
        value = "40",
        date = "2-9-2023",
        unit_text = R.string.weight_unit,
        cardTitle = R.string.weight,
        result = "Normal",
        iconAndResultColor = Pair(Color.Cyan, Color.Green),
        textStyle = MaterialTheme.typography.displayMedium,
    )
}