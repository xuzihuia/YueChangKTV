package com.yuechang.ktv.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Toast 扩展函数
 */

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

fun Context.showLongToast(message: String) {
    showToast(message, Toast.LENGTH_LONG)
}

fun Context.showLongToast(@StringRes resId: Int) {
    showToast(resId, Toast.LENGTH_LONG)
}