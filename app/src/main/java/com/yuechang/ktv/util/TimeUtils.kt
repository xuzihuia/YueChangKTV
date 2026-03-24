package com.yuechang.ktv.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间格式化工具
 */
object TimeUtils {
    
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    
    fun formatDuration(millis: Long): String {
        val seconds = millis / 1000
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }
    
    fun formatDate(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }
    
    fun formatTime(timestamp: Long): String {
        return timeFormat.format(Date(timestamp))
    }
    
    fun formatDateTime(timestamp: Long): String {
        return dateTimeFormat.format(Date(timestamp))
    }
    
    fun formatRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        return when {
            diff < 60 * 1000 -> "刚刚"
            diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)}分钟前"
            diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)}小时前"
            diff < 7 * 24 * 60 * 60 * 1000 -> "${diff / (24 * 60 * 60 * 1000)}天前"
            else -> formatDate(timestamp)
        }
    }
}