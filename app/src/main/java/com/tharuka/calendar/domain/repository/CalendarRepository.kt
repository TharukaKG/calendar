package com.tharuka.calendar.domain.repository

import com.tharuka.calendar.domain.model.PrivateEvent
import com.tharuka.calendar.domain.model.PublicEvent

interface CalendarRepository {

    fun getPublicEvents(): List<PublicEvent>

    suspend fun getPrivateEvents(): List<PrivateEvent>

    suspend fun addNewPrivateEvent(privateEvent: PrivateEvent)

}