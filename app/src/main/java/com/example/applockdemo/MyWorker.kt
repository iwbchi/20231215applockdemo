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



//      workerが作られている間通知が表示される
//        delay(5000L)
        val getter = GetUsageStats(applicationContext)
        for (usage in getter.getUsageStats()) {
            val usageTime = usage.getTotalTimeInForeground()
            if (usageTime != 0L) println(usage.packageName)
//            println(usage.getTotalTimeInForeground())
        }

//        val intent = Intent(applicationContext, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        applicationContext.startActivity(intent)
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
}