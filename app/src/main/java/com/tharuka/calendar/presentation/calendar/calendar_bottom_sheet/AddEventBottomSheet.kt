package com.tharuka.calendar.presentation.calendar.calendar_bottom_sheet


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tharuka.calendar.R
import com.tharuka.calendar.common.EventType
import com.tharuka.calendar.domain.model.PrivateEvent
import com.tharuka.calendar.util.getDefaultReminders
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEventBottomSheet(
    onClickAddEvent:(PrivateEvent)->Unit,
    state: BottomSheetState
) {

    var textTitle by remember { mutableStateOf(TextFieldValue("")) }
    var note by remember { mutableStateOf(TextFieldValue("")) }
    var reminder by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedStartTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    var pickedEndTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedStartTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedStartTime)
        }
    }

    val formattedEndTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedEndTime)
        }
    }

    var btnEnableState by remember {
        mutableStateOf(false)
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeStartDialogState = rememberMaterialDialogState()
    val timeEndDialogState = rememberMaterialDialogState()


    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    fun validate(){
        var isValid = true
        if(textTitle.text.isBlank()){
            isValid = false
        }
        if(pickedDate < LocalDate.now()){
            isValid  = false
        }

        if(pickedEndTime< pickedStartTime){
            isValid = false
        }

        btnEnableState = isValid
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            // bottom sheet title
            Text(
                text = stringResource(id = R.string.create_new_event),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            //event title field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = textTitle,
                onValueChange = {
                    validate()
                    textTitle = it
                },
                label = { Text(text = (stringResource(id = R.string.title))) },
                placeholder = { Text(stringResource(id = R.string.enter_title)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
            Spacer(modifier = Modifier.height(16.dp))
            // event date field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = formattedDate,
                readOnly = true,
                onValueChange = {validate()},
                label = { Text(text = (stringResource(id = R.string.date))) },
                placeholder = { Text(stringResource(id = R.string.enter_date)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = stringResource(id = R.string.date_icon),
                        Modifier.clickable { dateDialogState.show() })
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // event time period
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                // from time
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.45f),
                    value = formattedStartTime,
                    readOnly = true,
                    onValueChange = {validate()},
                    label = { Text(text = (stringResource(id = R.string.from))) },
                    placeholder = { Text(stringResource(id = R.string.time)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = stringResource(id = R.string.clock_icon),
                        Modifier.clickable { timeStartDialogState.show() })
                    }
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                //to time
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    value = formattedEndTime,
                    readOnly = true,
                    onValueChange = {validate()},
                    label = { Text(text = (stringResource(id = R.string.to))) },
                    placeholder = { Text(stringResource(id = R.string.time)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = stringResource(id = R.string.clock_icon),
                        Modifier.clickable { timeEndDialogState.show() })
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // event note
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = note,
                onValueChange = { note = it },
                label = { Text(text = (stringResource(id = R.string.note))) },
                placeholder = { Text(stringResource(id = R.string.enter_note)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
            Spacer(modifier = Modifier.height(16.dp))

            // event reminder time selector
            Column{
                OutlinedTextField(
                    value = reminder,
                    onValueChange = { reminder = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = (stringResource(id = R.string.remind))) },
                    placeholder = { Text(stringResource(id = R.string.default_reminder)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = {
                        Icon(icon, stringResource(id = R.string.drop_down_icon),
                            Modifier.clickable { expanded = !expanded })
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    getDefaultReminders().map {
                        DropdownMenuItem(onClick = {
                            reminder = it.first
                            expanded = false
                        }) {
                            Text(text = it.first)
                        }
                    }
                }
            }
            //save button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                onClick = {
                    onClickAddEvent(
                        PrivateEvent(
                            title = textTitle.text,
                            day = pickedDate.dayOfMonth,
                            month = pickedDate.month.value,
                            year = pickedDate.year,
                            hourStart = pickedStartTime.hour,
                            minuteStart = pickedStartTime.minute,
                            hourEnd = pickedEndTime.hour,
                            minuteEnd = pickedEndTime.minute,
                            eventType = EventType.PRIVATE,
                            description = note.text
                        )
                    )
                    scope.launch { state.collapse() }
                },
                enabled = btnEnableState,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = stringResource(id = R.string.create_reminder),
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {}
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",

        ) {
            pickedDate = it
        }
    }

    MaterialDialog(
        dialogState = timeStartDialogState,
        buttons = {
            positiveButton(text = "Ok") {}
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Pick a time",
        ) {
            pickedStartTime = it
        }
    }

    MaterialDialog(
        dialogState = timeEndDialogState,
        buttons = {
            positiveButton(text = "Ok") {}
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Pick a time",
        ) {
            pickedEndTime = it
        }
    }

}
