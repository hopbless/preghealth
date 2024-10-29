package com.hopeinyang.preg_health.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreHealthTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateToSettings: () -> Unit,
    isDoctorAccount: Boolean = true,
    navigateBack: () -> Unit
){
    MediumTopAppBar(
        modifier = Modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        title = { Text(text = "P-Health") },
        navigationIcon = {
                if (isDoctorAccount){
                IconButton(onClick =  navigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {

            if (!isDoctorAccount){
                IconButton(onClick = navigateToSettings) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Localized description"
                    )
                }
            }



        }, scrollBehavior = scrollBehavior

    )
}
