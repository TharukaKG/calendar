package com.tharuka.calendar.presentation.calendar.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tharuka.calendar.common.EventType

@Composable
fun EventList(
    modifier: Modifier = Modifier,
    eventList:List<Event>
){
    Box(modifier = modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(eventList){ event->
                when(event.event){
                    EventType.PRIVATE -> PrivateEventItem(event = event)
                    else -> PublicEventItem(event = event)
                }
            }
        }
    }
}