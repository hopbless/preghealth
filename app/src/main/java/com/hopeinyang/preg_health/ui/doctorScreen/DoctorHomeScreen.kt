package com.hopeinyang.preg_health.ui.doctorScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hopeinyang.preg_health.R
import com.hopeinyang.preg_health.components.CardTitle
import com.hopeinyang.preg_health.data.dto.UserInfo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorScreen(
    viewModel: DoctorScreenViewModel = hiltViewModel(),
    navigateTo: (String, String) -> Unit,
    navigateToSettings: (String,) -> Unit,
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primaryContainer,

    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.primaryContainer,

            topBar = { CenterAlignedTopAppBar(
                modifier = Modifier,
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer

                ),
                title = { Text(text = "Doctor Screen") },
                navigationIcon = {},
                actions = {

                    IconButton(onClick = {
                        viewModel.menuIconClick(navigateToSettings)

                    }) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                    }

                }
            )
            },
        ) {contentPadding ->
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(top = 20.dp)

            ) {
                Text(
                    text = "List of Patients",
                    style = TextStyle(
                        background = MaterialTheme.colorScheme.tertiaryContainer,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer

                    )

                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 15.dp),
                    thickness = 3.dp,
                    color = Color.Green

                )

                if (uiState.patientList.isNotEmpty()){

                    LazyColumn(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        state = LazyListState()
                    ){
                        items(uiState.patientList){
                            PatientCardItem(it, viewModel, navigateTo)

                        }
                    }


                }else{



                    Text(
                        text = "You have no patient at the moment, please check back later",
                        style = TextStyle(
                            background = MaterialTheme.colorScheme.tertiaryContainer,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onTertiaryContainer

                        ),
                        modifier = Modifier.padding(16.dp)


                    )
                }

            }




        }
    }
}


@Composable
fun PatientCardItem(
    userInfo: UserInfo,
    viewModel: DoctorScreenViewModel,
    onClick: (String, String) -> Unit
){
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 4.dp),
        onClick = {viewModel.onPatientSelected(userInfo.userId, onClick)}
    ) {
        CardTitle(cardTitle = "${userInfo.firstName} ${userInfo.lastName}")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()

        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.person_3_fill),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
            ) {
                Text(text = "Age: ${userInfo.age}", fontSize = 20.sp,)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "No Weeks of Pregnancy: ${userInfo.pregnancyWeek}", fontSize = 20.sp,)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Phone Number: ${userInfo.phoneNumber}", fontSize = 20.sp,)


            }



        }


    }
}

