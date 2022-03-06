package com.example.aomatatask.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.aomatatask.base.BaseFragment

fun Context.color(id: Int): Int {
    return ContextCompat.getColor(this, id)
}



//for fragment
fun BaseFragment.checkPermission(
    context: Context,
    permissionArray: Array<String>,
    requestCode: Int
): Boolean {
    var isPermissionAllowed = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        for (permission in permissionArray) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                isPermissionAllowed = false
                break
            }
        }
        return if (!isPermissionAllowed) {
            this.requestPermissions(permissionArray, requestCode)
            isPermissionAllowed

        } else {
            isPermissionAllowed
        }
    } else {
        return isPermissionAllowed
    }
}