package com.vgg.meditationtracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vgg.meditationtracker.data.database.SessionType
import com.vgg.meditationtracker.ui.components.MoodSelector
import com.vgg.meditationtracker.ui.theme.getColorForSessionType
import com.vgg.meditationtracker.ui.viewmodel.MeditationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSessionScreen(viewModel: MeditationViewModel, onNavigateBack: () -> Unit) {
    var selectedType by remember { mutableStateOf(SessionType.MEDITATION) }
    var duration by remember { mutableStateOf("10") }
    var selectedMood by remember { mutableStateOf(3) }
    var notes by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val message by viewModel.message.collectAsState()

    LaunchedEffect(message) {
        if (message?.contains("exitosamente") == true) {
            onNavigateBack()
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Nueva Sesión") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            }
        )
    }) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(paddingValues)) {

            // Tipo (lista simple)
            Text("Tipo", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row {
                SessionType.values().forEach { type ->
                    val isSelected = type == selectedType
                    FilterChip(selected = isSelected, onClick = { selectedType = type }, label = { Text(type.name.lowercase().replaceFirstChar { it.uppercase() }) }, colors = FilterChipDefaults.filterChipColors(containerColor = getColorForSessionType(type).copy(alpha = if (isSelected) 0.25f else 0.08f)))
                    Spacer(Modifier.width(8.dp))
                }
            }

            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = duration, onValueChange = { duration = it }, label = { Text("Duración (min)") })
            Spacer(Modifier.height(12.dp))
            Text("Estado de ánimo", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            MoodSelector(selectedMood = selectedMood, onMoodSelected = { selectedMood = it })

            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Notas (opcional)") }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(24.dp))

            Button(onClick = {
                val dur = duration.toIntOrNull()
                if (dur == null || dur <= 0) {
                    showError = true
                    errorMessage = "Duración inválida"
                    return@Button
                }
                viewModel.insertSession(selectedType, dur, selectedMood, notes)
            }, enabled = !isLoading) {
                Text("Guardar")
            }
            if (showError) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
