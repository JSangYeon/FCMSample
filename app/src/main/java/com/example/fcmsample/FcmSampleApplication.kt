package com.example.fcmsample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FcmSampleApplication : Application() {

    companion object {
        lateinit var instance: FcmSampleApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}