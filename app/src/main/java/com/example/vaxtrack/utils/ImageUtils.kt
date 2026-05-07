package com.example.vaxtrack.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.UUID

object ImageUtils {

    /**
     * Convertit une image en Bitmap depuis une Uri.
     */
    suspend fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } catch (e: Exception) {
                null
            }
        }
    }

    /**
     * Compresse un Bitmap en JPEG et retourne un ByteArray.
     */
    fun compressBitmap(bitmap: Bitmap, quality: Int = 80): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        return stream.toByteArray()
    }

    /**
     * Upload une image dans Supabase Storage et retourne l’URL publique.
     */
    suspend fun uploadImage(storage: Storage, bucketName: String, imageBytes: ByteArray): String {
        val fileName = "${UUID.randomUUID()}.jpg"
        storage.from(bucketName).upload(fileName, imageBytes)
        return storage.from(bucketName).publicUrl(fileName)
    }
}
