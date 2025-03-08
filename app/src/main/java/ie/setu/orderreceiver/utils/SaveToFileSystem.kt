package ie.setu.orderreceiver.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

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

}