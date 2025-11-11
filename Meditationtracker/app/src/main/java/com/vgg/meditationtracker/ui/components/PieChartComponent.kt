package com.vgg.meditationtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Canvas
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vgg.meditationtracker.data.database.TypeDuration
import com.vgg.meditationtracker.ui.theme.getColorForSessionType
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@Composable
fun PieChartCard(data: List<TypeDuration>) {
    if (data.isEmpty()) return

    val total = data.sumOf { it.total }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Distribución por tipo", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            PieChart(data = data, total = total)
        }
    }
}

@Composable
fun PieChart(data: List<TypeDuration>, total: Int) {
    // Canvas para el pastel
    Canvas(modifier = Modifier.size(200.dp)) {
        var startAngle = -90f
        data.forEach { entry ->
            val sweep = 360f * (entry.total / total.toFloat())
            drawArc(
                color = getColorForSessionType(entry.type),
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = true,
                size = Size(size.width, size.height)
            )
            startAngle += sweep
        }
    }
    Spacer(Modifier.height(8.dp))
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        data.forEach {
            LegendItem(color = getColorForSessionType(it.type), text = "${it.type.name} (${it.total} min)")
        }
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        // Box con background requiere import androidx.compose.foundation.background
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}
