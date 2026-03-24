package com.yuechang.ktv.service.audio

/**
 * 歌词解析器
 * 支持标准LRC格式和增强格式
 */
class LyricsParser {
    
    /**
     * 解析LRC歌词
     * @param lrcContent LRC格式歌词内容
     * @return 歌词行列表
     */
    fun parseLrc(lrcContent: String): List<LyricLine> {
        val lines = mutableListOf<LyricLine>()
        val timePattern = Regex("\\[(\\d{2}):(\\d{2})[.:](\\d{2,3})\\]")
        
        lrcContent.lines().forEach { line ->
            val matches = timePattern.findAll(line)
            val text = line.replace(timePattern, "").trim()
            
            if (text.isNotEmpty()) {
                matches.forEach { match ->
                    val (minutes, seconds, millis) = match.destructured
                    val time = minutes.toLong() * 60 * 1000 +
                            seconds.toLong() * 1000 +
                            millis.toLong() * (if (millis.length == 2) 10 else 1)
                    
                    lines.add(LyricLine(time, 0, text))
                }
            }
        }
        
        // 按时间排序并计算持续时间
        val sortedLines = lines.sortedBy { it.startTime }
        for (i in sortedLines.indices) {
            if (i < sortedLines.size - 1) {
                sortedLines[i] = sortedLines[i].copy(
                    duration = sortedLines[i + 1].startTime - sortedLines[i].startTime
                )
            } else {
                // 最后一行，默认5秒
                sortedLines[i] = sortedLines[i].copy(duration = 5000)
            }
        }
        
        return sortedLines
    }
    
    /**
     * 解析增强歌词（带音高信息）
     * 格式: [mm:ss.xx]<pitch>歌词文本
     */
    fun parseEnhancedLrc(lrcContent: String): List<LyricLine> {
        val lines = mutableListOf<LyricLine>()
        val pattern = Regex("\\[(\\d{2}):(\\d{2})[.:](\\d{2,3})\\]<(-?\\d+)>(.+)")
        
        lrcContent.lines().forEach { line ->
            val match = pattern.find(line)
            if (match != null) {
                val (minutes, seconds, millis, pitch, text) = match.destructured
                val time = minutes.toLong() * 60 * 1000 +
                        seconds.toLong() * 1000 +
                        millis.toLong() * (if (millis.length == 2) 10 else 1)
                
                lines.add(LyricLine(time, 0, text.trim(), pitch.toIntOrNull()))
            }
        }
        
        // 计算持续时间
        val sortedLines = lines.sortedBy { it.startTime }
        for (i in sortedLines.indices) {
            if (i < sortedLines.size - 1) {
                sortedLines[i] = sortedLines[i].copy(
                    duration = sortedLines[i + 1].startTime - sortedLines[i].startTime
                )
            }
        }
        
        return sortedLines
    }
    
    /**
     * 根据时间获取当前歌词行索引
     */
    fun getCurrentLineIndex(lines: List<LyricLine>, currentTime: Long): Int {
        if (lines.isEmpty()) return -1
        
        var index = -1
        for (i in lines.indices) {
            if (lines[i].startTime <= currentTime) {
                index = i
            } else {
                break
            }
        }
        return index
    }
    
    /**
     * 获取指定时间之前的歌词（用于显示历史）
     */
    fun getPreviousLines(
        lines: List<LyricLine>,
        currentIndex: Int,
        count: Int
    ): List<LyricLine> {
        if (currentIndex < 0 || lines.isEmpty()) return emptyList()
        
        val startIndex = (currentIndex - count).coerceAtLeast(0)
        return lines.subList(startIndex, currentIndex)
    }
    
    /**
     * 获取指定时间之后的歌词（用于预览）
     */
    fun getNextLines(
        lines: List<LyricLine>,
        currentIndex: Int,
        count: Int
    ): List<LyricLine> {
        if (currentIndex < 0 || lines.isEmpty()) return emptyList()
        
        val endIndex = (currentIndex + count + 1).coerceAtMost(lines.size)
        return if (currentIndex + 1 < endIndex) {
            lines.subList(currentIndex + 1, endIndex)
        } else {
            emptyList()
        }
    }
}

/**
 * 歌词行
 */
data class LyricLine(
    val startTime: Long,       // 开始时间（毫秒）
    val duration: Long,        // 持续时间（毫秒）
    val text: String,          // 歌词文本
    val pitch: Int? = null     // 音高（可选）
) {
    val endTime: Long get() = startTime + duration
    
    /**
     * 获取行内进度 0.0 - 1.0
     */
    fun getProgress(currentTime: Long): Float {
        return if (duration > 0) {
            ((currentTime - startTime).toFloat() / duration).coerceIn(0f, 1f)
        } else 0f
    }
    
    /**
     * 判断时间点是否在这一行内
     */
    fun containsTime(currentTime: Long): Boolean {
        return currentTime in startTime until endTime
    }
}