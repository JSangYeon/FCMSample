package com.example.fcmsample.ui.activity

import android.os.Bundle
import com.example.fcmsample.R
import com.example.fcmsample.databinding.ActivitySecondBinding
import com.example.fcmsample.ui.base.BaseActivity

class SecondActivity : BaseActivity<ActivitySecondBinding>(R.layout.activity_second) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = intent.getStringExtra("title") // FCM title
        binding.tvBody.text = intent.getStringExtra("body") // FCM body

    }
}