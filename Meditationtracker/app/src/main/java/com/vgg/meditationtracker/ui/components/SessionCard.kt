package com.vgg.meditationtracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vgg.meditationtracker.data.database.MeditationSession
import com.vgg.meditationtracker.ui.theme.getColorForSessionType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SessionCard(session: MeditationSession, onDelete: (MeditationSession) -> Unit) {
    val backgroundColor = getColorForSessionType(session.type).copy(alpha = 0.1f)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = session.type.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = getColorForSessionType(session.type)
                )
                Spacer(Modifier.height(4.dp))
                Text("Duración: ${session.durationMinutes} min", style = MaterialTheme.typography.bodyMedium)
                Text("Ánimo: ${session.mood}/5", style = MaterialTheme.typography.bodyMedium)
                Text(dateFormat.format(Date(session.date)), style = MaterialTheme.typography.bodySmall)
                if (session.notes.isNotBlank()) {
                    Text("Notas: ${session.notes}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
            IconButton(onClick = { onDelete(session) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
