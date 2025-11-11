package com.vgg.meditationtracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoodSelector(selectedMood: Int, onMoodSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        (1..5).forEach { mood ->
            val color = when (mood) {
                1 -> Color(0xFFEF5350) // rojo
                2 -> Color(0xFFFFA726) // naranja
                3 -> Color(0xFFFFEB3B) // amarillo
                4 -> Color(0xFF81C784) // verde
                else -> Color(0xFF4CAF50) // verde intenso
            }
            val emoji = when (mood) {
                1 -> "😠" // Muy disgustado
                2 -> "😟" // Disgustado
                3 -> "😐" // Neutral
                4 -> "🙂" // Satisfecho
                else -> "😄" // Muy satisfecho
            }

            IconButton(onClick = { onMoodSelected(mood) }) {
                Text(
                    text = emoji,
                    fontSize = if (mood == selectedMood) 32.sp else 24.sp,
                    modifier = Modifier.size(if (mood == selectedMood) 40.dp else 32.dp),
                    color = if (mood == selectedMood) color else Color.Gray
                )
            }
        }
    }
}
