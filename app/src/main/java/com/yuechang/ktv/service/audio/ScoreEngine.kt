package com.yuechang.ktv.service.audio

import kotlin.math.abs
import kotlin.math.sqrt

/**
 * 评分引擎
 * 基于音高匹配度计算得分
 */
class ScoreEngine {
    
    companion object {
        private const val PITCH_TOLERANCE = 1      // 音高容差（半音）
        private const val TIMING_TOLERANCE = 200L  // 时间容差（毫秒）
    }
    
    /**
     * 计算得分
     * @param userPitches 用户演唱音高数据
     * @param standardPitches 标准音高数据
     * @return 评分结果
     */
    fun calculateScore(
        userPitches: List<PitchPoint>,
        standardPitches: List<PitchPoint>
    ): ScoreResult {
        if (userPitches.isEmpty() || standardPitches.isEmpty()) {
            return ScoreResult(0, "C")
        }
        
        // 音准评分 (70%权重)
        val pitchScore = calculatePitchScore(userPitches, standardPitches)
        
        // 节奏评分 (30%权重)
        val rhythmScore = calculateRhythmScore(userPitches, standardPitches)
        
        // 稳定性评分
        val stabilityScore = calculateStabilityScore(userPitches, standardPitches)
        
        // 综合得分
        val totalScore = pitchScore * 0.7 + rhythmScore * 0.3
        
        val score = totalScore.toInt().coerceIn(0, 100)
        val level = getLevel(score)
        
        return ScoreResult(
            score = score,
            level = level,
            pitchScore = pitchScore,
            rhythmScore = rhythmScore,
            stabilityScore = stabilityScore
        )
    }
    
    /**
     * 计算音准得分
     */
    private fun calculatePitchScore(
        userPitches: List<PitchPoint>,
        standardPitches: List<PitchPoint>
    ): Double {
        var totalScore = 0.0
        var matchCount = 0
        
        for (userPoint in userPitches) {
            // 找到对应时间点的标准音高
            val standardPoint = findClosestPitch(userPoint.time, standardPitches)
            if (standardPoint != null) {
                val pitchDiff = abs(userPoint.pitch - standardPoint.pitch)
                val score = when {
                    pitchDiff <= PITCH_TOLERANCE -> 100.0
                    pitchDiff <= PITCH_TOLERANCE * 2 -> 85.0
                    pitchDiff <= PITCH_TOLERANCE * 3 -> 70.0
                    pitchDiff <= PITCH_TOLERANCE * 5 -> 50.0
                    else -> 30.0
                }
                totalScore += score
                matchCount++
            }
        }
        
        return if (matchCount > 0) totalScore / matchCount else 0.0
    }
    
    /**
     * 计算节奏得分
     */
    private fun calculateRhythmScore(
        userPitches: List<PitchPoint>,
        standardPitches: List<PitchPoint>
    ): Double {
        var totalScore = 0.0
        var matchCount = 0
        
        for (standardPoint in standardPitches) {
            val userPoint = findClosestPitch(standardPoint.time, userPitches)
            if (userPoint != null) {
                val timingDiff = abs(userPoint.time - standardPoint.time)
                val score = when {
                    timingDiff <= TIMING_TOLERANCE / 2 -> 100.0
                    timingDiff <= TIMING_TOLERANCE -> 80.0
                    timingDiff <= TIMING_TOLERANCE * 2 -> 60.0
                    else -> 40.0
                }
                totalScore += score
                matchCount++
            }
        }
        
        return if (matchCount > 0) totalScore / matchCount else 0.0
    }
    
    /**
     * 计算稳定性得分
     */
    private fun calculateStabilityScore(
        userPitches: List<PitchPoint>,
        standardPitches: List<PitchPoint>
    ): Double {
        val deviations = mutableListOf<Double>()
        
        for (userPoint in userPitches) {
            val standardPoint = findClosestPitch(userPoint.time, standardPitches)
            if (standardPoint != null) {
                deviations.add(abs(userPoint.pitch - standardPoint.pitch).toDouble())
            }
        }
        
        if (deviations.isEmpty()) return 0.0
        
        // 计算标准差
        val mean = deviations.average()
        val variance = deviations.map { (it - mean) * (it - mean) }.average()
        val stdDev = sqrt(variance)
        
        // 标准差越小，稳定性越高
        return (100 - stdDev * 10).coerceIn(0.0, 100.0)
    }
    
    /**
     * 查找最接近的音高数据
     */
    private fun findClosestPitch(time: Long, pitches: List<PitchPoint>): PitchPoint? {
        return pitches.minByOrNull { abs(it.time - time) }
            ?.takeIf { abs(it.time - time) <= TIMING_TOLERANCE * 2 }
    }
    
    /**
     * 获取等级
     */
    fun getLevel(score: Int): String {
        return when {
            score >= 95 -> "SSS"
            score >= 90 -> "SS"
            score >= 85 -> "S"
            score >= 75 -> "A"
            score >= 60 -> "B"
            score >= 40 -> "C"
            else -> "D"
        }
    }
}

/**
 * 音高数据点
 */
data class PitchPoint(
    val time: Long,       // 时间点（毫秒）
    val pitch: Int,       // 音高值（MIDI音符编号）
    val duration: Long    // 持续时间
)