package com.songzhen.mylibrary

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

//kotlin声明回调的方式1 简化
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

//    kotlin声明回调的方式2 复杂
//    private var callback: ((Boolean, List<String>) -> Unit)? = null

    private var callback: PermissionCallback? = null

    fun requestNow(callback: PermissionCallback, vararg permission: String) {
        this.callback = callback
        requestPermissions(permission, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }


}


