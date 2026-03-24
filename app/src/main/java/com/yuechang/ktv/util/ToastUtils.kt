package com.yuechang.ktv.util

import android.content.Context
import android.widget.Toast

/**
 * Toast工具类
 */
object ToastUtils {
    
    private var toast: Toast? = null
    
    fun show(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT).apply { show() }
    }
    
    fun showLong(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG).apply { show() }
    }
}