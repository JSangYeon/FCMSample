package com.example.fcmsample.ui.activity

import android.os.Bundle
import android.util.Log
import com.example.fcmsample.R
import com.example.fcmsample.databinding.ActivitySecondBinding
import com.example.fcmsample.ui.base.BaseActivity

class SecondActivity : BaseActivity<ActivitySecondBinding>(R.layout.activity_second) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = getString(R.string.id)
        val missedRequests = getString(R.string.missed_requests)
        val addAnyDataHere = getString(R.string.add_any_data_here)
        Log.d("MyFirebaseMsgService SecondActivity", "id : ${intent.getStringExtra(id)}")
        Log.d("MyFirebaseMsgService SecondActivity", "missedRequests : ${intent.getStringExtra(missedRequests)}")

        binding.tvId.text = intent.getStringExtra(id) // FCM id
        binding.tvMissedRequests.text = intent.getStringExtra(missedRequests) // FCM missedRequests

    }
}