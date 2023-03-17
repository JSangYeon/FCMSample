package com.example.fcmsample.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.fcmsample.R
import com.example.fcmsample.ui.activity.SecondActivity

class NotificationActionService : Service() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        intent.action?.let { action ->

            when (action) {
                getString(R.string.action_check) -> {
                    checkAction()
                }
                getString(R.string.action_cancel) -> {} // 취소 버튼 클릭 시
            }
            cancelAllNotification()
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkAction() {
//        val moveSecondActivityIntent = Intent(this, SecondActivity::class.java)
//        moveSecondActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        val pendingIntent = PendingIntent.getActivity(
//            this, 0 /* Request code */, moveSecondActivityIntent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//
////        startForegroundService(moveSecondActivityIntent)
//
//        pendingIntent.

    }


    // 알림 창 종료
    private fun cancelAllNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
}

