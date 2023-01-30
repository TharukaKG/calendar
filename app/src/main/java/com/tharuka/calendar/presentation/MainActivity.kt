package com.tharuka.calendar.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tharuka.calendar.presentation.calendar.calendar.CalendarScreen
import com.tharuka.calendar.presentation.calendar.calendar_permissions.PermissionScreen
import com.tharuka.calendar.presentation.ui.theme.CalendarTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            CalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PermissionScreen.route
                    ) {
                        composable(
                            route = Screen.PermissionScreen.route
                        ){
                            PermissionScreen(navController = navController)
                        }
                        composable(
                            route = Screen.CalendarScreen.route
                        ){
                            CalendarScreen()
                        }
                    }
                }
            }
        }
    }

}
