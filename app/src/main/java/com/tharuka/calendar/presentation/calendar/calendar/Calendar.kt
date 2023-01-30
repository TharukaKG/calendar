package com.tharuka.calendar.presentation.calendar.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.viewinterop.AndroidView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import java.util.*

private const val TAG = "Calendarx"

@Composable
fun Calendar(
    eventDays: List<EventDay>,
    onSelectPrevious: (Int)->Unit,
    onSelectNext: (Int)->Unit,
    onClickDay: (Calendar)->Unit
) {

    AndroidView(
        factory = { CalendarView(it) },
        update = { calendarView->

            calendarView.setMinimumDate(Calendar.getInstance().apply { set(2022, 11, 31) })
            calendarView.setMaximumDate(Calendar.getInstance().apply { set(2023, 11, 31) })

            calendarView.setEvents(eventDays)

            calendarView.setOnPreviousPageChangeListener(object : OnCalendarPageChangeListener{
                override fun onChange() {
                    onSelectPrevious(calendarView.currentPageDate.get(Calendar.MONTH))
                }
            })

            calendarView.setOnForwardPageChangeListener(object : OnCalendarPageChangeListener{
                override fun onChange() {
                    onSelectNext(calendarView.currentPageDate.get(Calendar.MONTH))
                }
            })

            calendarView.setOnDayClickListener(object :OnDayClickListener{
                override fun onDayClick(eventDay: EventDay) {
                    onClickDay(eventDay.calendar)
                }
            })

        }
    )
}