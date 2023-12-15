package com.example.applockdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.app.usage.UsageStatsManager

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = "MyChannelId"
        val channelName = "My Channel"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId).apply {
            setContentTitle("Service is running")
            setContentText("This is a notification from a Foreground Service.")
            setSmallIcon(R.mipmap.ic_launcher)
        }.build()

        startForeground(1, notification)
        Handler().postDelayed({
            println("called service delay")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }, 10000) // 10秒後にMainActivityを表示
        return START_STICKY
    }
}

