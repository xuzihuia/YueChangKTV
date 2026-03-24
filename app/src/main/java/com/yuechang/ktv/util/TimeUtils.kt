package com.yuechang.ktv.util

/**
 * 时间工具类
 */
object TimeUtils {
    
    /**
     * 格式化时长 mm:ss
     */
    fun formatDuration(ms: Long): String {
        val seconds = ms / 1000
        val min = seconds / 60
        val sec = seconds % 60
        return String.format("%02d:%02d", min, sec)
    }
    
    /**
     * 格式化播放量
     */
    fun formatPlayCount(count: Long): String {
        return when {
            count >= 100000000 -> String.format("%.1f亿", count / 100000000.0)
            count >= 10000 -> String.format("%.1f万", count / 10000.0)
            else -> count.toString()
        }
    }
    
    /**
     * 格式化相对时间
     */
    fun formatRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        return when {
            diff < 60 * 1000 -> "刚刚"
            diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)}分钟前"
            diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)}小时前"
            diff < 7 * 24 * 60 * 60 * 1000 -> "${diff / (24 * 60 * 60 * 1000)}天前"
            diff < 30 * 24 * 60 * 60 * 1000 -> "${diff / (7 * 24 * 60 * 60 * 1000)}周前"
            diff < 365 * 24 * 60 * 60 * 1000L -> "${diff / (30 * 24 * 60 * 60 * 1000)}个月前"
            else -> "${diff / (365 * 24 * 60 * 60 * 1000)}年前"
        }
    }
}