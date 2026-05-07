package com.example.vaxtrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vaxtrack.viewmodel.VaccinationViewModel

@Composable
fun DashboardScreen(viewModel: VaccinationViewModel) {
    val totalDoses = viewModel.totalDoses.collectAsState().value
    val menages = viewModel.menages.collectAsState().value

    val zonesCouverte = menages.count { it.status == "Couverte" }
    val zonesEnCours = menages.count { it.status == "En cours" }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Tableau de bord",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        StatCard(
            title = "Total des doses",
            value = "$totalDoses",
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        StatCard(
            title = "Zones couvertes",
            value = "$zonesCouverte",
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        StatCard(
            title = "Zones en cours",
            value = "$zonesEnCours",
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun StatCard(title: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = color
            )
        }
    }
}
