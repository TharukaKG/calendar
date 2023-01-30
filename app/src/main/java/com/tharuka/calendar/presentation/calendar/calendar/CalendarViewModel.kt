package com.tharuka.calendar.presentation.calendar.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applandeo.materialcalendarview.EventDay
import com.tharuka.calendar.common.Resource
import com.tharuka.calendar.domain.use_case.AddNewPrivateEventUseCase
import com.tharuka.calendar.domain.use_case.GetPrivateEventsUseCase
import com.tharuka.calendar.domain.use_case.GetPublicEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getPublicEventsUseCase: GetPublicEventsUseCase,
    private val getPrivateEventsUseCase: GetPrivateEventsUseCase,
    private val addNewPrivateEventUseCase: AddNewPrivateEventUseCase
) : ViewModel() {

    private val _eventsState = MutableStateFlow(EventState())
    val eventState:StateFlow<EventState> get() = _eventsState

    init {
//        getEvents()
//        onUiEvent(UiEvent.OnMonthChanged())
    }

    private fun getEvents() {
        getPrivateEventsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    val publicEvents = getPublicEventsUseCase()

                    val privateEventsConverted = resource.data?.map { event ->
                        Event(
                            day = event.day,
                            month = event.month,
                            description = event.title,
                            event = event.eventType,
                            startTime = "${event.hourStart}: ${event.hourEnd}",
                            note = event.description
                        )
                    } ?: emptyList()

                    val publicEventsConverted = publicEvents.map { event ->
                        Event(
                            day = event.day,
                            month = event.month,
                            description = event.title,
                            event = event.event
                        )
                    }

                    val allEvents = publicEventsConverted + privateEventsConverted
                    _eventsState.value = _eventsState.value.copy(allEvents = allEvents)
                }
                is Resource.Error -> {
                    //
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onUiEvent(uiEvent: UiEvent) = viewModelScope.launch{
        when (uiEvent) {
            is UiEvent.OnMonthChanged -> {
                _eventsState.update { it ->
                    val monthEvents =
                        _eventsState.value.allEvents.filter { uiEvent.month == it.month }
                            .distinctBy { it.day }
                    val monthEventDays = monthEvents.map {
                        Calendar.getInstance().let { calendar ->
                            calendar.set(it.year, it.month, it.day)
                            EventDay(calendar, it.event.icon)
                        }
                    }
                    it.copy(monthEvents = monthEvents, monthEventDays = monthEventDays)
                }
            }
            is UiEvent.OnAddNewPrivateEvent ->{
                addNewPrivateEventUseCase.invoke(uiEvent.privateEvent)
                getEvents()
            }
        }
    }

}