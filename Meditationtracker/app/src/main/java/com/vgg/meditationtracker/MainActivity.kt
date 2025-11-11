package com.vgg.meditationtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vgg.meditationtracker.data.database.MeditationDatabase
import com.vgg.meditationtracker.data.repository.MeditationRepository
import com.vgg.meditationtracker.navigation.NavigationGraph
import com.vgg.meditationtracker.ui.theme.MeditationTrackerTheme
import com.vgg.meditationtracker.ui.viewmodel.MeditationViewModel
import com.vgg.meditationtracker.ui.viewmodel.MeditationViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = MeditationDatabase.getDatabase(applicationContext)
        val repository = MeditationRepository(database.meditationDao())

        setContent {
            MeditationTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val viewModel: MeditationViewModel = viewModel(factory = MeditationViewModelFactory(repository))
                    NavigationGraph(viewModel = viewModel)
                }
            }
        }
    }
}
