package com.tharuka.calendar.presentation.calendar.calendar

import com.tharuka.calendar.domain.model.PrivateEvent

sealed class UiEvent{
    class OnMonthChanged(val month:Int = 0): UiEvent()
    class OnAddNewPrivateEvent(val privateEvent: PrivateEvent): UiEvent()
}
