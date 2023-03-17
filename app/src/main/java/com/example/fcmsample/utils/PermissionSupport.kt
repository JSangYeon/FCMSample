package com.example.fcmsample.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PermissionSupport constructor(private val activity: Activity, private val context: Context) {


    // Manifset에 권한을 작성 후
    // 요청할 권한을 배열로 저장
    private val permissions = getPermissionList()

    // 권한 요청을 할 때 발생하는 창에 대한 결과값
    private var permissionList: MutableList<String> = ArrayList()

    private val MULTIPLE_PERMISSIONS = 1023


    // 허용할 권한 요청이 남았는지 체크
    fun checkPermission(): Boolean {
        var result: Int
        permissionList = ArrayList()

        // 배열로 저장한 권한 중 허용되지 않은 권한이 있는지 체크
        for (pm in permissions) {
            result = ContextCompat.checkSelfPermission(context, pm)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm)
            }
        }
        return permissionList.isEmpty()
    }

    // 권한 허용 요청
    fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity, permissionList.toTypedArray<String>(),
            MULTIPLE_PERMISSIONS
        )
    }

    private fun getPermissionList(): List<String> {

        val permissionList: MutableList<String> = ArrayList()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionList.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        return permissionList
    }

    // 권한 요청에 대한 결과 처리
    fun permissionResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ): Boolean {
        if (requestCode == MULTIPLE_PERMISSIONS && permissions.isNotEmpty() && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {

                // grantResults == 0 사용자가 허용한 것
                // grantResults == -1 사용자가 거부한 것
                if (grantResults[i] == -1) {
                    return false
                }
            }
        }
        return true
    }
}