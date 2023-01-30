package com.example.fcmsample.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.fcmsample.R
import com.example.fcmsample.databinding.ActivityMainBinding
import com.example.fcmsample.ui.base.BaseActivity
import com.example.fcmsample.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "fcmSample")
    }
}