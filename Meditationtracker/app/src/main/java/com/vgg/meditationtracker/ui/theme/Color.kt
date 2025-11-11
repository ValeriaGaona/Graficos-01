package com.vgg.meditationtracker.ui.theme

import androidx.compose.ui.graphics.Color
import com.vgg.meditationtracker.data.database.SessionType

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

fun getColorForSessionType(type: SessionType): Color = when (type) {
    SessionType.MEDITATION -> Color(0xFF81C784)
    SessionType.BREATHING -> Color(0xFF4FC3F7)
    SessionType.YOGA -> Color(0xFFBA68C8)
    SessionType.JOURNALING -> Color(0xFFFFB74D)
    SessionType.GRATITUDE -> Color(0xFFFF8A65)
}
