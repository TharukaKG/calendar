package com.tharuka.calendar.presentation.calendar.calendar

import com.applandeo.materialcalendarview.EventDay
import com.tharuka.calendar.common.EventType

data class EventState(
    val allEvents: List<Event> = listOf(),
    val monthEventDays:List<EventDay> = listOf(),
    val monthEvents:List<Event> = listOf(),
    val error:String = ""
)

data class Event(
    val day:Int = 1,
    val month:Int = 0,
    val year:Int = 2023,
    val timePeriod:String? = "",
    val description:String = "",
    val event: EventType,
    val startTime: String? = null,
    val endTime: String? = null,
    val note: String = ""
)
