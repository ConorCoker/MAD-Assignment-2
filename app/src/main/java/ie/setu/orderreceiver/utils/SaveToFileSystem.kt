package ie.setu.orderreceiver.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object SaveToFileSystem {
    fun saveImageToAppStorage(
        uri: Uri,
        context: Context
    ): Uri? {
        val resolver = context.contentResolver
        val inputStream = resolver.openInputStream(uri)
        val outputFile = File(context.filesDir, "image_${System.currentTimeMillis()}.jpg")

        inputStream?.use { input ->
            FileOutputStream(outputFile).use { output ->
                input.copyTo(output)
            }
        }
        return Uri.fromFile(outputFile)
    }

    object FirebaseUploader {
        private val storage: FirebaseStorage = Firebase.storage

        suspend fun uploadImageToFirebase(uri: Uri, context: Context): String? {
            return try {
                val fileName = "images/${UUID.randomUUID()}.jpg"
                val ref = storage.reference.child(fileName)
                val uploadTask = ref.putFile(uri).await()
                val downloadUrl = ref.downloadUrl.await().toString()
                Log.d("FirebaseUploader", "Image uploaded: $downloadUrl")
                downloadUrl
            } catch (e: Exception) {
                Log.e("FirebaseUploader", "Upload failed", e)
                null
            }
        }
    }

}