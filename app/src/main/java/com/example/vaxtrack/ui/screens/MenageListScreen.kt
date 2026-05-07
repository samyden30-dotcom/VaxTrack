package com.example.vaxtrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vaxtrack.data.model.Menage
import com.example.vaxtrack.viewmodel.VaccinationViewModel
import com.example.vaxtrack.ui.components.HouseholdCard

@Composable
fun MenageListScreen(viewModel: VaccinationViewModel) {
    val menages = viewModel.menages.collectAsState().value
    val totalDoses = viewModel.totalDoses.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Total des doses administrées : $totalDoses",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(menages) { menage ->
                HouseholdCard(menage = menage)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
