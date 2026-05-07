package com.example.vaxtrack.data.repository

import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class PhotoRepository(private val storage: Storage) {

    private val bucketName = "photos_menages"

    /**
     * Upload une image dans Supabase Storage et retourne l’URL publique.
     */
    suspend fun uploadPhoto(imageBytes: ByteArray): String = withContext(Dispatchers.IO) {
        val fileName = "${UUID.randomUUID()}.jpg"
        storage.from(bucketName).upload(fileName, imageBytes)
        return@withContext storage.from(bucketName).publicUrl(fileName)
    }

    /**
     * Supprime une image du bucket Supabase Storage.
     */
    suspend fun deletePhoto(fileName: String): Boolean = withContext(Dispatchers.IO) {
        storage.from(bucketName).delete(listOf(fileName))
        return@withContext true
    }

    /**
     * Liste toutes les images disponibles dans le bucket.
     */
    suspend fun listPhotos(): List<String> = withContext(Dispatchers.IO) {
        val files = storage.from(bucketName).list()
        return@withContext files.map { it.name }
    }

    /**
     * Récupère l’URL publique d’une image existante.
     */
    fun getPhotoUrl(fileName: String): String {
        return storage.from(bucketName).publicUrl(fileName)
    }
}
