package com.example.fcmsample.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.fcmsample.R
import com.example.fcmsample.databinding.ActivityMainBinding
import com.example.fcmsample.ui.base.BaseActivity
import com.example.fcmsample.ui.viewmodel.MainViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val _mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "fcmSample")

        binding.mainViewModel = _mainViewModel



        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task -> // FCM 토큰 구하기
            if (!task.isSuccessful) {
                Log.w(logTag, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Log.d(logTag, "token : $token")
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })


    }
}