package com.example.applockdemo

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import java.util.Calendar

private const val TAG = "GetUsageStats"

class GetUsageStats(
    private val context: Context
) {

    fun getUsageStats(): List<UsageStats>{
        Log.i(TAG,"Accessed GetUsageStatsClass")
        return sortedUsageStats( getAppUsageStats())
    }

    private fun getAppUsageStats(): MutableList<UsageStats> {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -30)

        // usageStatsManagerのオブジェクトの取得
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            cal.timeInMillis,
            System.currentTimeMillis()
        )
    }

    private fun sortedUsageStats(usageStats: MutableList<UsageStats>): List<UsageStats> {
        usageStats.sortWith(
            compareBy { it.lastTimeUsed }
        )
        return usageStats
    }
}
