package com.tharuka.calendar.data.dto

import com.tharuka.calendar.domain.model.PrivateEvent
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

data class PrivateEventDto(
    val eventId:String,
    val title:String,
    val endTime:Long,
    val startTime:Long,
    val description: String
)

fun List<PrivateEventDto>.toPrivateEvent(): List<PrivateEvent>{

    return map { event->
        val calendarStart = Calendar.getInstance().apply {
            time = Date(event.startTime)
        }
        val calendarEnd = Calendar.getInstance().apply {
            time = Date(event.endTime)
        }

        PrivateEvent(
            title = event.title,
            day = calendarStart.get(Calendar.DATE),
            month = calendarStart.get(Calendar.MONTH),
            year = calendarStart.get(Calendar.YEAR),
            hourStart = calendarStart.get(Calendar.HOUR_OF_DAY),
            minuteStart =  calendarStart.get(Calendar.MINUTE),
            hourEnd = calendarEnd.get(Calendar.HOUR_OF_DAY),
            minuteEnd = calendarEnd.get(Calendar.MINUTE),
            description = ""
        )
    }
}

fun PrivateEvent.toPrivateEventDto(): PrivateEventDto{
    return PrivateEventDto(
        eventId = Random().nextLong().toString(),
        title = title,
        endTime = ZonedDateTime.of(year, month, day, hourEnd, minuteEnd, 0, 0, ZoneId.systemDefault()).toInstant().toEpochMilli(),
        startTime = ZonedDateTime.of(year, month, day, hourStart, minuteStart, 0, 0, ZoneId.systemDefault()).toInstant().toEpochMilli(),
        description = ""
    )
}