package com.hopeinyang.preg_health.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.ui.theme.PreGHealthTheme

@Composable
fun BMICard(
    weight:String,
    height:Float
){
    Surface(color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)

    ) {
        Card(shape = RoundedCornerShape(4.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onBackground
            ), modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .shadow(20.dp)


        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(painter = painterResource(id = R.drawable.person_3_fill), contentDescription = null)
                Text(text = "Body Mass Index", fontSize = 25.sp)
                VerticalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    thickness = 1.dp, color = DividerDefaults.color)
                val bmi = Math.round(weight.toFloat() / (height*height)).toString()
                Text(
                    text = bmi, fontSize = 30.sp,
                    modifier = Modifier
                        .padding()
                )
                
            }
        }

    }
}
@Preview(showBackground =true)
@Composable
private fun weightCardPreview(){
    PreGHealthTheme {
        BMICard("65", 3.1f)

    }

}