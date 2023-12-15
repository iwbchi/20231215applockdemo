package com.example.applockdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay


class MyWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

    override suspend fun doWork(): Result {
        println("call doWrok")
        setForeground(createForegroundInfo("progress"))
        delay(5000L)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(intent)
        return Result.success()
    }

    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val id = "id"
        val title = "title"
        val cancel = "channel"
        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            // Add the cancel action to the notification which can
            // be used to cancel the worker
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()



        return ForegroundInfo(10, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val name = "channel_name"
        val descriptionText = "channel_description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel_id", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        notificationManager.createNotificationChannel(channel)
    }
//        private val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as
//                    NotificationManager
//
//        override suspend fun doWork(): Result {
//            val inputUrl = inputData.getString(KEY_INPUT_URL)
//                ?: return Result.failure()
//            val outputFile = inputData.getString(KEY_OUTPUT_FILE_NAME)
//                ?: return Result.failure()
//            // Mark the Worker as important
//            val progress = "Starting Download"
//            setForeground(createForegroundInfo(progress))
//            download(inputUrl, outputFile)
//            return Result.success()
//    }
//
//    private fun download(inputUrl: String, outputFile: String) {
//        // Downloads a file and updates bytes read
//        // Calls setForeground() periodically when it needs to update
//        // the ongoing Notification
//    }
//    // Creates an instance of ForegroundInfo which can be used to update the
//    // ongoing notification.
//    private fun createForegroundInfo(progress: String): ForegroundInfo {
//        val id = "id"
//        val title = "title"
//        val cancel = "channel"
//        // This PendingIntent can be used to cancel the worker
//        val intent = WorkManager.getInstance(applicationContext)
//            .createCancelPendingIntent(getId())
//
//        // Create a Notification channel if necessary
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createChannel()
//        }
//
//        val notification = NotificationCompat.Builder(applicationContext, id)
//            .setContentTitle(title)
//            .setTicker(title)
//            .setContentText(progress)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setOngoing(true)
//            // Add the cancel action to the notification which can
//            // be used to cancel the worker
//            .addAction(android.R.drawable.ic_delete, cancel, intent)
//            .build()
//
//        return ForegroundInfo(10, notification)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createChannel() {
//        // Create a Notification channel
//    }
//
//    companion object {
//        const val KEY_INPUT_URL = "KEY_INPUT_URL"
//        const val KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME"
//    }

}