package com.example.fcmsample.ui.viewmodel

import android.util.Log
import com.example.fcmsample.ui.base.BaseViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    init{
        getFirebaseToken()
    }
    private fun getFirebaseToken() {
        Log.d(logTag, "getFirebaseToken")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(logTag,"Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            val token = task.result
            Log.d(logTag, "token=${token}")
        })
    }
}