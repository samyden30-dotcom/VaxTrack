package com.example.vaxtrack.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.vaxtrack.data.model.Menage
import com.example.vaxtrack.viewmodel.VaccinationViewModel
import java.io.ByteArrayOutputStream
import java.io.File

@Composable
fun FormulaireMenageScreen(viewModel: VaccinationViewModel) {
    val context = LocalContext.current
    var chefMenage by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var nbPersonnes by remember { mutableStateOf("") }
    var nbDoses by remember { mutableStateOf("") }
    
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var photoUrl by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    // --- Galerie ---
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            val bytes = uriToBytes(context, it)
            if (bytes != null) {
                isUploading = true
                viewModel.uploadMenagePhoto(bytes) { url ->
                    isUploading = false
                    if (url != null) photoUrl = url else errorMessage = "Échec upload"
                }
            }
        }
    }

    // --- Caméra ---
    val tempFile = remember { File.createTempFile("camera_photo", ".jpg", context.cacheDir) }
    val tempUri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", tempFile)

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            selectedImageUri = tempUri
            val bytes = uriToBytes(context, tempUri)
            if (bytes != null) {
                isUploading = true
                viewModel.uploadMenagePhoto(bytes) { url ->
                    isUploading = false
                    if (url != null) photoUrl = url else errorMessage = "Échec upload"
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState)) {
        Text("Nouvelle intervention", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = chefMenage,
            onValueChange = { chefMenage = it },
            label = { Text("Chef de ménage") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contact,
            onValueChange = { contact = it },
            label = { Text("Contact") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nbPersonnes,
            onValueChange = { nbPersonnes = it },
            label = { Text("Nombre de personnes") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nbDoses,
            onValueChange = { nbDoses = it },
            label = { Text("Nombre de doses") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { galleryLauncher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
            Text("Choisir une photo (Galerie)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { cameraLauncher.launch(tempUri) }, modifier = Modifier.fillMaxWidth()) {
            Text("Prendre une photo (Caméra)")
        }

        selectedImageUri?.let { uri ->
            val source = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.createSource(context.contentResolver, uri)
            } else null
            val bitmap: Bitmap? = try { source?.let { ImageDecoder.decodeBitmap(it) } } catch (e: Exception) { null }
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Image sélectionnée",
                    modifier = Modifier.size(200.dp).padding(top = 16.dp)
                )
            }
        }

        if (isUploading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        photoUrl?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Photo uploadée : $it")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val menage = Menage(
                    chefMenage = chefMenage,
                    contact = contact,
                    nbPersonnes = nbPersonnes.toIntOrNull() ?: 0,
                    nbDoses = nbDoses.toIntOrNull() ?: 0,
                    latitude = 0.0,
                    longitude = 0.0,
                    photoUrl = photoUrl
                )
                viewModel.ajouterMenage(menage)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isUploading
        ) {
            Text("Enregistrer")
        }
    }
}

fun uriToBytes(context: android.content.Context, uri: Uri): ByteArray? {
    return try {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
        } else {
            @Suppress("DEPRECATION")
            android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        outputStream.toByteArray()
    } catch (e: Exception) {
        null
    }
}
