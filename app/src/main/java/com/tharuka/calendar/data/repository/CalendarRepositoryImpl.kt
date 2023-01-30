package com.tharuka.calendar.data.repository

import com.tharuka.calendar.data.dto.toPrivateEvent
import com.tharuka.calendar.data.dto.toPrivateEventDto
import com.tharuka.calendar.data.local.calendar_provider.CalenderProvider
import com.tharuka.calendar.data.local.getPublicEventsList
import com.tharuka.calendar.domain.model.PrivateEvent
import com.tharuka.calendar.domain.model.PublicEvent
import com.tharuka.calendar.domain.repository.CalendarRepository
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarProvider: CalenderProvider
): CalendarRepository {

    override fun getPublicEvents(): List<PublicEvent> {
        return getPublicEventsList()
    }

    override suspend fun getPrivateEvents(): List<PrivateEvent> {
        return calendarProvider.getEvents().toPrivateEvent()
    }

    override suspend fun addNewPrivateEvent(privateEvent: PrivateEvent) {
        calendarProvider.insertNewEvent(privateEvent.toPrivateEventDto())
    }
}