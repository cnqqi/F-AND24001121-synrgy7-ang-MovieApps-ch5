package com.synrgy.movieapp.worker

import android.content.Context
import android.net.Uri
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File
import java.io.FileOutputStream
import java.util.*

class UploadImageWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val imageUri = inputData.getString("imageUri") ?: return Result.failure()
        val context = applicationContext
        val file = File(context.cacheDir, UUID.randomUUID().toString() + ".jpg")

        try {
            val inputStream = context.contentResolver.openInputStream(Uri.parse(imageUri))
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            // Simulate upload by returning the local file path as the uploaded URL
            val imageUrl = file.absolutePath
            val outputData = workDataOf("imageUrl" to imageUrl)

            return Result.success(outputData)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }
}