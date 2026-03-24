package com.yuechang.ktv.util

import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.yuechang.ktv.R

/**
 * ImageView 扩展函数
 */

fun ImageView.loadImage(url: String?, placeholder: Int = R.drawable.ic_launcher_background) {
    val request = ImageRequest.Builder(context)
        .data(url)
        .placeholder(placeholder)
        .error(placeholder)
        .target(this)
        .build()
    
    ImageLoader(context).enqueue(request)
}

fun ImageView.loadCircleImage(url: String?, placeholder: Int = R.drawable.ic_launcher_background) {
    // TODO: 实现圆形图片加载
    loadImage(url, placeholder)
}