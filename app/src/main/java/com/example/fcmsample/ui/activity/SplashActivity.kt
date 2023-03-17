package com.example.fcmsample.ui.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.fcmsample.R
import com.example.fcmsample.databinding.ActivitySplashBinding
import com.example.fcmsample.ui.base.BaseActivity
import com.example.fcmsample.utils.PermissionSupport


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    lateinit var permissionSupport: PermissionSupport

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        val data = intent.extras;
        Log.d(logTag, "스플래시 bundle : $data")
        Log.d(logTag, "스플래시 data : ${data?.getString("testchannel")}")
        Log.d(logTag, "스플래시 data : ${data?.getString("title")}")
        Log.d(logTag, "스플래시 notification : ${data?.getString("content")}")
        Log.d(logTag, "스플래시 key : ${data?.getString("key")}")

        Log.d("스플래시 액티비티","here")

        permissionCheck()
    }

    // 권한 체크
    private fun permissionCheck() {
        // 클래스 객체 생성
        permissionSupport = PermissionSupport(this, this)

        // 권한 체크한 후에 리턴이 false일 경우 권한 요청을 해준다.
        if (!permissionSupport.checkPermission()) {
            permissionSupport.requestPermission()
        }
        else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Request Permission에 대한 결과 값을 받는다.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissionSupport.permissionResult(
                requestCode,
                permissions,
                grantResults
            )
        ) // 모든 권한 승인한 경우
        {;
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        } else { // 거부한 권한 있을 경우
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this@SplashActivity)
            dlg.setTitle(getString(R.string.app_permission_not_allowed_title))
            dlg.setMessage(getString(R.string.app_permission_not_allowed_content))
            dlg.setPositiveButton(
                getString(R.string.check),
                DialogInterface.OnClickListener { dialog, which -> //토스트 메시지
                    finish() //Dialog로 바꿀 것
                })
            dlg.setOnCancelListener { // Dialog cancel 했을 경우
                finish()
            }
            dlg.show()
        }
    }
}