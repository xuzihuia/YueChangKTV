package com.yuechang.ktv.service.audio

import kotlin.math.abs
import kotlin.math.sqrt

/**
 * 评分引擎
 * 对标唱享K歌智能打分系统
 */
class ScoreEngine {
    
    // 音高偏差阈值（半音）
    private val pitchTolerance = 0.5
    
    // 节奏偏差阈值（毫秒）
    private val timingTolerance = 200L
    
    /**
     * 计算得分
     * @param userPitches 用户演唱音高数据
     * @param standardPitches 标准音高数据
     * @return 得分 (0-100)
     */
    fun calculateScore(
        userPitches: List<PitchData>,
        standardPitches: List<PitchData>
    ): ScoreResult {
        if (userPitches.isEmpty() || standardPitches.isEmpty()) {
            return ScoreResult(0, 0.0, 0.0, 0.0)
        }
        
        var totalPitchScore = 0.0
        var totalTimingScore = 0.0
        var matchCount = 0
        var totalNotes = standardPitches.size
        
        for (standard in standardPitches) {
            // 找到用户对应时间段的音高
            val userPitch = userPitches.find { 
                abs(it.time - standard.time) <= timingTolerance 
            }
            
            if (userPitch != null) {
                // 计算音高偏差
                val pitchDiff = abs(userPitch.pitch - standard.pitch)
                val pitchScore = when {
                    pitchDiff <= pitchTolerance -> 100.0
                    pitchDiff <= pitchTolerance * 2 -> 80.0
                    pitchDiff <= pitchTolerance * 3 -> 60.0
                    else -> 40.0
                }
                
                // 计算节奏偏差
                val timingDiff = abs(userPitch.time - standard.time)
                val timingScore = when {
                    timingDiff <= timingTolerance / 2 -> 100.0
                    timingDiff <= timingTolerance -> 80.0
                    timingDiff <= timingTolerance * 2 -> 60.0
                    else -> 40.0
                }
                
                totalPitchScore += pitchScore
                totalTimingScore += timingScore
                matchCount++
            }
        }
        
        // 计算平均分
        val avgPitchScore = if (matchCount > 0) totalPitchScore / matchCount else 0.0
        val avgTimingScore = if (matchCount > 0) totalTimingScore / matchCount else 0.0
        
        // 综合得分（音高70% + 节奏30%）
        val finalScore = (avgPitchScore * 0.7 + avgTimingScore * 0.3)
        
        // 稳定性（音准方差）
        val stability = calculateStability(userPitches, standardPitches)
        
        return ScoreResult(
            score = finalScore.toInt().coerceIn(0, 100),
            pitchAccuracy = avgPitchScore,
            timingAccuracy = avgTimingScore,
            stability = stability
        )
    }
    
    /**
     * 计算稳定性
     */
    private fun calculateStability(
        userPitches: List<PitchData>,
        standardPitches: List<PitchData>
    ): Double {
        val deviations = mutableListOf<Double>()
        
        for (user in userPitches) {
            val standard = standardPitches.find { 
                abs(it.time - user.time) <= timingTolerance 
            }
            standard?.let {
                deviations.add(abs(user.pitch - it.pitch).toDouble())
            }
        }
        
        if (deviations.isEmpty()) return 0.0
        
        // 计算标准差
        val mean = deviations.average()
        val variance = deviations.map { (it - mean) * (it - mean) }.average()
        val stdDev = sqrt(variance)
        
        // 转换为稳定性分数（标准差越小，稳定性越高）
        return (100 - stdDev * 10).coerceIn(0.0, 100.0)
    }
    
    /**
     * 获取等级评价
     */
    fun getScoreLevel(score: Int): ScoreLevel {
        return when {
            score >= 95 -> ScoreLevel.SSS
            score >= 90 -> ScoreLevel.SS
            score >= 85 -> ScoreLevel.S
            score >= 75 -> ScoreLevel.A
            score >= 60 -> ScoreLevel.B
            score >= 40 -> ScoreLevel.C
            else -> ScoreLevel.D
        }
    }
}

/**
 * 音高数据
 */
data class PitchData(
    val time: Long,        // 时间点（毫秒）
    val pitch: Int,        // 音高值（MIDI note number）
    val duration: Long     // 持续时间
)

/**
 * 得分结果
 */
data class ScoreResult(
    val score: Int,            // 总分
    val pitchAccuracy: Double, // 音准
    val timingAccuracy: Double,// 节奏
    val stability: Double      // 稳定性
)

/**
 * 得分等级
 */
enum class ScoreLevel(val displayName: String, val color: Int) {
    SSS("SSS", 0xFFFFD700.toInt()),  // 金色
    SS("SS", 0xFFFFD700.toInt()),
    S("S", 0xFFFFD700.toInt()),
    A("A", 0xFF00FF00.toInt()),      // 绿色
    B("B", 0xFF00BFFF.toInt()),      // 蓝色
    C("C", 0xFF808080.toInt()),      // 灰色
    D("D", 0xFFFF0000.toInt())       // 红色
}