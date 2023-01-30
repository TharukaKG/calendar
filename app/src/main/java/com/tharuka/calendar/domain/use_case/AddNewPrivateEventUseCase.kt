package com.tharuka.calendar.domain.use_case

import com.tharuka.calendar.domain.model.PrivateEvent
import com.tharuka.calendar.domain.repository.CalendarRepository
import javax.inject.Inject

class AddNewPrivateEventUseCase @Inject constructor(private val calendarRepository: CalendarRepository) {

     suspend operator fun invoke(privateEvent: PrivateEvent){
         calendarRepository.addNewPrivateEvent(privateEvent)
    }

}