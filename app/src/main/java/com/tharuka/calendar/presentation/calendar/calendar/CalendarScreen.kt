package com.tharuka.calendar.presentation.calendar.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.tharuka.calendar.presentation.calendar.calendar_bottom_sheet.AddEventBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel()){

    val state by remember { viewModel.eventState }.collectAsState()
    val scope = rememberCoroutineScope()

    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetBackgroundColor = Color.White,

        sheetContent = {
            AddEventBottomSheet(
                onClickAddEvent = {viewModel.onUiEvent(UiEvent.OnAddNewPrivateEvent(it))},
                state = sheetState
            )
        }) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Calendar(
                eventDays = state.monthEventDays,
                onSelectPrevious = { month -> viewModel.onUiEvent(UiEvent.OnMonthChanged(month)) },
                onSelectNext = { month -> viewModel.onUiEvent(UiEvent.OnMonthChanged(month)) },
                onClickDay = { scope.launch { if(sheetState.isCollapsed){sheetState.expand()} } }
            )
            EventList(eventList = state.monthEvents)
        }

    }

}

