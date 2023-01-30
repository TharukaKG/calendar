package com.tharuka.calendar.domain.use_case

import com.tharuka.calendar.domain.model.PublicEvent
import com.tharuka.calendar.domain.repository.CalendarRepository
import javax.inject.Inject

class GetPublicEventsUseCase @Inject constructor(private val calendarRepository: CalendarRepository) {

    operator fun invoke(): List<PublicEvent>{
        return calendarRepository.getPublicEvents()
    }

}