package com.yuechang.ktv.service.audio

/**
 * 歌词解析器
 * 支持 LRC 格式歌词解析
 */
class LyricsParser {
    
    /**
     * 解析 LRC 歌词
     * @param lrcContent LRC 格式歌词内容
     * @return 歌词行列表
     */
    fun parseLrc(lrcContent: String): List<LyricLine> {
        val lines = mutableListOf<LyricLine>()
        
        lrcContent.lines().forEach { line ->
            // 解析时间标签 [mm:ss.xx]
            val timePattern = Regex("\\[(\\d{2}):(\\d{2})\\.(\\d{2,3})\\](.*)")
            val match = timePattern.find(line)
            
            if (match != null) {
                val (minutes, seconds, millis, text) = match.destructured
                val time = minutes.toLong() * 60 * 1000 + 
                          seconds.toLong() * 1000 + 
                          millis.toLong() * (if (millis.length == 2) 10 else 1)
                
                lines.add(LyricLine(
                    time = time,
                    duration = 0, // 将在后续计算
                    text = text.trim()
                ))
            }
        }
        
        // 计算每行持续时间
        for (i in lines.indices) {
            if (i < lines.size - 1) {
                lines[i] = lines[i].copy(duration = lines[i + 1].time - lines[i].time)
            }
        }
        
        return lines.sortedBy { it.time }
    }
    
    /**
     * 解析增强歌词（带音高信息）
     */
    fun parseEnhancedLrc(lrcContent: String): List<LyricLine> {
        val lines = parseLrc(lrcContent)
        
        // TODO: 解析音高信息
        // 格式: [mm:ss.xx]<pitch>歌词
        
        return lines
    }
    
    /**
     * 根据当前时间获取歌词行
     */
    fun getCurrentLine(lines: List<LyricLine>, currentTime: Long): Int {
        if (lines.isEmpty()) return -1
        
        var index = -1
        for (i in lines.indices) {
            if (lines[i].time <= currentTime) {
                index = i
            } else {
                break
            }
        }
        return index
    }
}

/**
 * 歌词行
 */
data class LyricLine(
    val time: Long,       // 开始时间（毫秒）
    val duration: Long,   // 持续时间（毫秒）
    val text: String,     // 歌词文本
    val pitch: Int? = null // 音高（可选）
) {
    val endTime: Long get() = time + duration
    
    /**
     * 获取当前行内的进度 (0.0 - 1.0)
     */
    fun getProgress(currentTime: Long): Float {
        return if (duration > 0) {
            ((currentTime - time).toFloat() / duration).coerceIn(0f, 1f)
        } else 0f
    }
    
    /**
     * 判断时间点是否在这一行内
     */
    fun containsTime(currentTime: Long): Boolean {
        return currentTime in time until endTime
    }
}