package com.tharuka.calendar.presentation

sealed class Screen(val route:String) {
    object PermissionScreen:Screen("permission_screen")
    object CalendarScreen:Screen("calendar_screen")
}