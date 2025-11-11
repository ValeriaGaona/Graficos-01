package com.vgg.meditationtracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vgg.meditationtracker.data.repository.SessionStatistics
import com.vgg.meditationtracker.ui.theme.getColorForSessionType

@Composable
fun StatisticsCard(statistics: SessionStatistics) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Estadísticas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Total sesiones: ${statistics.totalSessions}")
            Text("Total minutos: ${statistics.totalMinutes}")
            Text("Promedio minutos: ${statistics.averageMinutes}")
            Text("Estado de ánimo promedio: ${"%.1f".format(statistics.averageMood)} / 5")
            Text(
                "Tipo más frecuente: ${statistics.mostFrequentType.name}",
                color = getColorForSessionType(statistics.mostFrequentType)
            )
        }
    }
}
