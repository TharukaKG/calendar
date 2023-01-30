package com.tharuka.calendar.presentation.calendar.calendar_permissions

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.tharuka.calendar.presentation.Screen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(
navController: NavController
) {

    // Camera permission state
    val calendarPermission = rememberMultiplePermissionsState(
        permissions =
        listOf(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        )
    )

    val xxx = rememberPermissionState(permission = Manifest.permission.READ_CALENDAR)

    if (xxx.status.isGranted) {
        Log.i("PermissionScreen", "PermissionScreen: ")
        navController.navigate(Screen.CalendarScreen.route)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val textToShow = if (xxx.status.shouldShowRationale) {
                "The calendar is important for this app. Please grant the permission."
            } else {
                "Calendar permission required for this feature to be available. " +
                        "Please grant the permission"
            }
            Text(textToShow, textAlign = TextAlign.Center)
            TextButton(
                onClick = { calendarPermission.launchMultiplePermissionRequest() }
            ) {
                Text(
                    text = "Give Permissions",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}