package com.example.vaxtrack.utils

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

class LocationHelper(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Récupère la localisation actuelle de l’appareil.
     * Nécessite la permission ACCESS_FINE_LOCATION ou ACCESS_COARSE_LOCATION.
     */
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Coordinates? {
        val location = fusedLocationClient.lastLocation.await()
        return location?.let {
            Coordinates(
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }
}
