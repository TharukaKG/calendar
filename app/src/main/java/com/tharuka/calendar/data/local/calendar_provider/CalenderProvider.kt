package com.tharuka.calendar.data.local.calendar_provider

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import com.tharuka.calendar.CalendarApplication
import com.tharuka.calendar.data.dto.PrivateEventDto
import com.tharuka.calendar.domain.model.PrivateEvent
import java.util.*

private const val START_DATE_2023:String = "1672377300000"
 private const val ORDER:String = "ASC"

class CalenderProvider {

    companion object{
        const val calendarId: Int = 3
    }

    private val projection: Array<String> = arrayOf(
        CalendarContract.Events._ID,
        CalendarContract.Events.TITLE,
        CalendarContract.Events.DTSTART,
        CalendarContract.Events.DTEND,
        CalendarContract.Events.DESCRIPTION,
        CalendarContract.Events.CALENDAR_ID,
    )

    fun getEvents(): List<PrivateEventDto>{

        val selection = "${CalendarContract.Events.DTSTART} >= $START_DATE_2023 AND ${CalendarContract.Events.CALENDAR_ID} = $calendarId"
        val sortOrder = "${CalendarContract.Events.DTSTART} $ORDER"

        val cursor: Cursor? = CalendarApplication.appContext.contentResolver.query(
            CalendarContract.Events.CONTENT_URI, projection, selection, null,
            sortOrder
        )

        val eventsList = mutableListOf<PrivateEventDto>()

        cursor?.apply {

            while (cursor.moveToNext()) {

                val id: String = cursor.getString(0)
                val title: String = cursor.getString(1)?:""
                val timeStart:Long = cursor.getLong(2)
                val timeEnd:Long = cursor.getLong(3)
                val description:String = cursor.getString(4)?:""

                eventsList.add(
                    PrivateEventDto(
                        eventId = id,
                        title = title,
                        endTime = timeEnd,
                        startTime = timeStart,
                        description = description
                    )
                )

            }

            cursor.close()
        }
        return eventsList
    }

    fun insertNewEvent(privateEventDto: PrivateEventDto){
        val contentResolver = CalendarApplication.appContext.contentResolver

        val values = ContentValues().apply {
            put(CalendarContract.Events.CALENDAR_ID, calendarId)
            put(CalendarContract.Events.TITLE, privateEventDto.title)
            put(CalendarContract.Events.DTSTART, privateEventDto.startTime)
            put(CalendarContract.Events.DTEND, privateEventDto.endTime)
            put(CalendarContract.Events.EVENT_TIMEZONE, "Indian/Christmas")
        }

        try {
            contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
        }catch (exception:Exception){
            throw exception
        }

    }

}