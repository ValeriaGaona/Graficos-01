package com.vgg.meditationtracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.vgg.meditationtracker.data.database.DailyMinutes
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BarChartCard(data: List<DailyMinutes>) {
    if (data.isEmpty()) return
    val maxValue = data.maxOf { it.totalMinutes }.coerceAtLeast(1)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Progreso semanal", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            BarChart(data = data, maxValue = maxValue)
        }
    }
}

@Composable
fun BarChart(data: List<DailyMinutes>, maxValue: Int) {
    val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)) {
        val barWidth = size.width / (data.size * 2)
        data.forEachIndexed { index, item ->
            val x = (index * 2 + 1) * barWidth
            val barHeight = (item.totalMinutes / maxValue.toFloat()) * size.height
            drawRoundRect(
                color = MaterialTheme.colorScheme.primary, // requires material3 import (ya presente)
                topLeft = Offset(x, size.height - barHeight),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(10f, 10f)
            )
        }
    }
}
