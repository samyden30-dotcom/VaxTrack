package com.example.vaxtrack.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.vaxtrack.remote.SupabaseClient
import io.github.jan.supabase.auth.auth
import com.example.vaxtrack.R

@Composable
fun SplashScreen(onNavigateNext: (String) -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000) // Affiche le splash pendant 2 secondes
        val session = SupabaseClient.client.auth.currentSessionOrNull()
        if (session == null) {
            onNavigateNext("login")
        } else {
            onNavigateNext("dashboard")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Logo VaxTrack
            Image(
                painter = painterResource(id = R.drawable.vaxtrack_logo),
                contentDescription = "Logo VaxTrack",
                modifier = Modifier.size(160.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("VaxTrack", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator()
        }
    }
}
