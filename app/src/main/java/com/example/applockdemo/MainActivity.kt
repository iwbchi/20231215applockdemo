package com.example.applockdemo
//
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
//    private var intentService: Intent? = null
//
//    private fun getPermissionUsageStats() {
//        if (!Settings.canDrawOverlays(this)) {
//            val intent = Intent(
//                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                Uri.parse("package:$packageName")
//            )
//            startActivity(intent)
////                launcher.launch(intent)
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startButton)
        val stopButton = findViewById<Button>(R.id.stopButton)

        startButton.setOnClickListener {
            if (Settings.canDrawOverlays(this)) {
                val myWorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
                    .setInitialDelay(5, TimeUnit.SECONDS)
                    .build()
                WorkManager.getInstance(this).enqueue(myWorkRequest)
            } else {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
//                launcher.launch(intent)
            }


        }
    }

}
