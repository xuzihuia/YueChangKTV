package com.yuechang.ktv.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * 得分环形进度条
 */
class ScoreRingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 12f
        color = Color.parseColor("#333333")
    }
    
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 12f
        strokeCap = Paint.Cap.ROUND
    }
    
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = 48f
        typeface = Typeface.DEFAULT_BOLD
    }
    
    private val levelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFD700")
        textAlign = Paint.Align.CENTER
        textSize = 24f
        typeface = Typeface.DEFAULT_BOLD
    }
    
    private var score = 0
    private var animatedScore = 0f
    private var targetScore = 0
    
    private val progressSweepAngle = 270f
    private val startAngle = 135f
    
    fun setScore(score: Int, animate: Boolean = true) {
        this.targetScore = score.coerceIn(0, 100)
        
        if (animate) {
            animateScore()
        } else {
            this.score = this.targetScore
            animatedScore = this.targetScore.toFloat()
            invalidate()
        }
    }
    
    private fun animateScore() {
        // 简单动画实现
        val step = (targetScore - score) / 20f
        if (abs(targetScore - animatedScore) > 1) {
            animatedScore += step
            score = animatedScore.toInt()
            postInvalidateDelayed(16)
        } else {
            score = targetScore
            animatedScore = targetScore.toFloat()
        }
        invalidate()
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(width, height) / 2f - 20f
        
        // 更新进度颜色
        progressPaint.color = getScoreColor(score)
        
        // 绘制背景环
        canvas.drawArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            startAngle,
            progressSweepAngle,
            false,
            backgroundPaint
        )
        
        // 绘制进度环
        val sweepAngle = progressSweepAngle * score / 100f
        canvas.drawArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            startAngle,
            sweepAngle,
            false,
            progressPaint
        )
        
        // 绘制分数
        canvas.drawText(
            score.toString(),
            centerX,
            centerY + 16f,
            textPaint
        )
        
        // 绘制等级
        val level = getScoreLevel(score)
        canvas.drawText(
            level,
            centerX,
            centerY + 50f,
            levelPaint
        )
    }
    
    private fun getScoreColor(score: Int): Int {
        return when {
            score >= 90 -> Color.parseColor("#FFD700") // 金色
            score >= 80 -> Color.parseColor("#FFD700")
            score >= 70 -> Color.parseColor("#FFD700")
            score >= 60 -> Color.parseColor("#4CAF50") // 绿色
            else -> Color.parseColor("#2196F3")        // 蓝色
        }
    }
    
    private fun getScoreLevel(score: Int): String {
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