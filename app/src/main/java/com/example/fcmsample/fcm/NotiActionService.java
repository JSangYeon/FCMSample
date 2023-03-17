package com.example.fcmsample.fcm;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotiActionService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ( intent == null )   return START_NOT_STICKY;
        String action = intent.getAction();
         if ( action.equals("CANCEL_ACTION") ) {

             cancelAllNotification();
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void cancelAllNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }


}
