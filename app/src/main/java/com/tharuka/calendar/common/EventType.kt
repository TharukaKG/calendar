package com.tharuka.calendar.common

import com.tharuka.calendar.R

enum class EventType(var description: String, val icon:Int) {
    PRIVATE(description = "My event", icon = R.drawable.icon_private),
    BANK_PUBLIC(description = "Bank and mercantile holiday", icon = R.drawable.icon_public_and_bank),
    BANK_PRIVATE(description = "Bank and My Event", icon = R.drawable.icon_bank_private),
    BANK_PUBLIC_PRIVATE(description = "Bank and mercantile holiday with my event", icon = R.drawable.icon_bank_public_private),
    BANK(description = "Bank holiday", icon = R.drawable.icon_bank),
}