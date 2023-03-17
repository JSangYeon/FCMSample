package com.example.fcmsample.fcm

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.fcmsample.R
import com.example.fcmsample.ui.activity.SecondActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {


    lateinit var notificationManager : NotificationManager

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        sendNotification() // 백그라운드인 경우 notification 이유는 모르겠지만 MainActivity로만 이동됨
        // Check if message contains a notification payload.
//        remoteMessage.data.let { data ->
//
//
//            Log.d(TAG, "Message Notification Body: ${data}")
//            sendNotification() // 백그라운드인 경우 notification 이유는 모르겠지만 MainActivity로만 이동됨
//
////            if (isAppRunning(this)) { //포그라운드
////                val intent = Intent(
////                    this,
////                    SecondActivity::class.java
////                ) // 전송될 Activity 지정 왜인지 백그라운드에선 Main으로만 감
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 이거여야지만 이동됨.. 이유는모름
////                intent.putExtra("title", notification.title )
////                intent.putExtra("body", notification.body )
////                startActivity(intent)
////
////            } else { // 백그라운드
////                sendNotification(remoteMessage.notification?.title) // 백그라운드인 경우 notification 이유는 모르겠지만 MainActivity로만 이동됨
////            }
//        }
        // Check if message contains a data payload.
//        if (remoteMessage.data.isNotEmpty()) {
//            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob()
//            } else {
//                // Handle message within 10 seconds
//                handleNow()
//            }
//        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .build()
        WorkManager.getInstance(this)
            .beginWith(work)
            .enqueue()
        // [END dispatch_job]
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    private fun sendNotification() {



        Log.d(TAG, "sendNotification")

//        if (messageBody == null) return
        val requestID = System.currentTimeMillis().toInt()

        val intent =
            Intent(this, SecondActivity::class.java) // 전송될 Activity 지정 왜인지 백그라운드에선 Main으로만 감
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        intent.putExtra("title", messageBody )
//        intent.putExtra("body", messageBody )

        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "fcm_default_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("FCM Message 테스트")
            .setContentText("테스트")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .addAction(com.google.android.material.R.drawable.ic_mtrl_checked_circle, "확인", getCheckPendingIntent())
            .addAction(com.google.android.material.R.drawable.ic_mtrl_chip_close_circle, "취소", getCancelPendingIntent())


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = notificationBuilder.build()

        Log.d(TAG, "sendNotification start")

        notificationManager.notify(
            5555 /* ID of notification */,
            notification
        ) // notification


    }

    //백그라운드일떄만 푸시메세지 보내고싶을 때 사용
    private fun isAppRunning(context: Context): Boolean {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val procInfos = activityManager.runningAppProcesses
        for (i in procInfos.indices) {
            if (procInfos[i].processName == context.packageName) {
                return true
            }
        }
        return false
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    internal class MyWorker(appContext: Context, workerParams: WorkerParameters) :
        Worker(appContext, workerParams) {
        override fun doWork(): Result {
            // TODO(developer): add long running task here.
            return Result.success()
        }
    }


    private fun getCheckPendingIntent(): PendingIntent {
        val broadcastIntentCancel = Intent(this, NotificationActionService::class.java)
        broadcastIntentCancel.action = getString(R.string.action_check)
        val pendingIntentCancel =if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getForegroundService(
                    this,
                    1,
                    broadcastIntentCancel,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getForegroundService(
                    this,
                    1,
                    broadcastIntentCancel,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
        } else {
            PendingIntent.getService(
                this,
                1,
                broadcastIntentCancel,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        return pendingIntentCancel
    }



    // 취소 버튼 눌렀을 때 PendingIntent
    private fun getCancelPendingIntent(): PendingIntent {

        val broadcastIntentCancel = Intent(this, NotificationActionService::class.java)
        broadcastIntentCancel.action = getString(R.string.action_cancel)
        val pendingIntentCancel =if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getForegroundService(
                    this,
                    1,
                    broadcastIntentCancel,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getForegroundService(
                    this,
                    1,
                    broadcastIntentCancel,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
        } else {
            PendingIntent.getService(
                this,
                1,
                broadcastIntentCancel,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        return pendingIntentCancel
    }

}