package com.tharuka.calendar.util

fun Float.toMinutes(): Int{
    return this.toInt() * 60
}

fun getDefaultReminders(): List<Pair<String, String>>{
    return listOf(
        Pair(first = "15 minutes", second = "15"),
        Pair(first = "1 hour", second = "6o"),
        Pair(first = "1 day", second = "1440"),
    )
}