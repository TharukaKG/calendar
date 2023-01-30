package com.tharuka.calendar.domain.model

import com.tharuka.calendar.common.EventType

data class PrivateEvent(
    val title: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val hourStart: Int,
    val minuteStart: Int,
    val hourEnd: Int,
    val minuteEnd: Int,
    val eventType: EventType = EventType.PRIVATE,
    val description:String
)