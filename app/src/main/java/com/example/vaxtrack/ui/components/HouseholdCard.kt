package com.example.vaxtrack.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vaxtrack.data.model.Menage

@Composable
fun HouseholdCard(menage: Menage) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Chef : ${menage.chefMenage}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Contact : ${menage.contact}")
            Text(text = "Personnes : ${menage.nbPersonnes}")
            Text(text = "Doses : ${menage.nbDoses}")
            Text(text = "Status : ${menage.status}", color = if (menage.status == "Couverte") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
        }
    }
}
