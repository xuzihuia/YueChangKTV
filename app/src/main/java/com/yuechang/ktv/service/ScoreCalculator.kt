package com.yuechang.ktv.service

object ScoreCalculator {
    fun calculate(pitchData: List<Int>, targetData: List<Int>): Int {
        if (pitchData.isEmpty() || targetData.isEmpty()) return 0
        
        var totalScore = 0
        val minSize = minOf(pitchData.size, targetData.size)
        
        for (i in 0 until minSize) {
            val diff = kotlin.math.abs(pitchData[i] - targetData[i])
            totalScore += when {
                diff <= 1 -> 100
                diff <= 3 -> 80
                diff <= 5 -> 60
                else -> 40
            }
        }
        
        return (totalScore / minSize).coerceIn(0, 100)
    }
    
    fun getLevel(score: Int): String {
        return when {
            score >= 95 -> "SSS"
            score >= 90 -> "SS"
            score >= 85 -> "S"
            score >= 75 -> "A"
            score >= 60 -> "B"
            else -> "C"
        }
    }
}