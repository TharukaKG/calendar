package com.tharuka.calendar.presentation.calendar.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tharuka.calendar.presentation.calendar.calendar.Event

@Composable
fun PublicEventItem(
    modifier: Modifier = Modifier,
    event: Event
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //date text circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(shape = CircleShape, color = MaterialTheme.colors.onPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = event.day.toString(),
                    textAlign = TextAlign.Center
                )
            }
            //space between date and description
            Spacer(modifier = Modifier.width(24.dp))
            // text description
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = event.description, style = MaterialTheme.typography.body1
                )
                Text(
                    text = event.event.description, style = MaterialTheme.typography.body1
                )
            }
        }
    }

}