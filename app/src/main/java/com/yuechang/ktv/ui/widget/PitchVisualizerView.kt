package com.yuechang.ktv.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * 音高可视化视图
 * 显示实时演唱音高曲线
 */
class PitchVisualizerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF6B35")
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }
    
    private val targetPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#4CAF50")
        strokeWidth = 2f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
    }
    
    private val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#333333")
        strokeWidth = 1f
    }
    
    private val dataPoints = mutableListOf<PitchPoint>()
    private val maxPoints = 100
    
    var currentPitch: Int = 0
        set(value) {
            field = value
            addDataPoint(value)
            invalidate()
        }
    
    var targetPitch: Int = 0
    
    data class PitchPoint(
        val pitch: Int,
        val time: Long = System.currentTimeMillis()
    )
    
    private fun addDataPoint(pitch: Int) {
        dataPoints.add(PitchPoint(pitch))
        if (dataPoints.size > maxPoints) {
            dataPoints.removeAt(0)
        }
    }
    
    fun clear() {
        dataPoints.clear()
        invalidate()
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val width = width.toFloat()
        val height = height.toFloat()
        
        // 绘制网格
        drawGrid(canvas, width, height)
        
        // 绘制目标音高线
        if (targetPitch > 0) {
            val targetY = pitchToY(targetPitch, height)
            canvas.drawLine(0f, targetY, width, targetY, targetPaint)
        }
        
        // 绘制音高曲线
        if (dataPoints.size > 1) {
            val path = Path()
            val stepX = width / maxPoints
            
            dataPoints.forEachIndexed { index, point ->
                val x = index * stepX
                val y = pitchToY(point.pitch, height)
                
                if (index == 0) {
                    path.moveTo(x.toFloat(), y)
                } else {
                    path.lineTo(x.toFloat(), y)
                }
            }
            
            canvas.drawPath(path, paint)
        }
    }
    
    private fun drawGrid(canvas: Canvas, width: Float, height: Float) {
        // 水平线 (音高刻度)
        for (i in 0..12) {
            val y = height * i / 12
            canvas.drawLine(0f, y, width, y, gridPaint)
        }
        
        // 垂直线 (时间刻度)
        for (i in 0..10) {
            val x = width * i / 10
            canvas.drawLine(x, 0f, x, height, gridPaint)
        }
    }
    
    private fun pitchToY(pitch: Int, height: Float): Float {
        // MIDI 音高范围: 36 (C2) - 84 (C6)
        val minPitch = 36
        val maxPitch = 84
        val normalizedPitch = (pitch - minPitch).coerceIn(0, maxPitch - minPitch)
        return height * (1 - normalizedPitch.toFloat() / (maxPitch - minPitch))
    }
}