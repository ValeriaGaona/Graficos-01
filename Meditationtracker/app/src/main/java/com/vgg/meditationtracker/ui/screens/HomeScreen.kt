package com.vgg.meditationtracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vgg.meditationtracker.ui.components.*
import com.vgg.meditationtracker.ui.viewmodel.MeditationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MeditationViewModel, onAddSessionClick: () -> Unit) {
    val sessions by viewModel.weeklySessions.collectAsState()
    val statistics by viewModel.statistics.collectAsState()
    val pieChartData by viewModel.pieChartData.collectAsState()
    val barChartData by viewModel.barChartData.collectAsState()
    val message by viewModel.message.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Column {
                    Text(text = "Meditación", style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Tu viaje de autocuidado", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer))
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddSessionClick, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar sesión")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            item {
                statistics?.let { StatisticsCard(statistics = it) }
            }
            item {
                PieChartCard(data = pieChartData)
            }
            item {
                BarChartCard(data = barChartData)
            }
            item {
                Text("Sesiones recientes", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
            }
            items(sessions) { session ->
                SessionCard(session = session, onDelete = { viewModel.deleteSession(it) })
            }
        }
    }

    LaunchedEffect(message) {
        // opcional: mostrar snackbar o toast
        viewModel.clearMessage()
    }
}
