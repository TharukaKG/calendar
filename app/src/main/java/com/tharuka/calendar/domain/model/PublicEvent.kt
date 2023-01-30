package com.tharuka.calendar.domain.model

import com.tharuka.calendar.common.EventType

data class PublicEvent(
    val title:String,
    val day: Int,
    val month: Int,
    val year: Int,
    val event: EventType
)

data class Holiday(
    val description:String,
    val icon: Int
)
