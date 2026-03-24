package com.yuechang.ktv.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.abs

/**
 * 歌词视图
 * 支持当前行高亮、滚动动画
 */
class LyricsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    
    private val normalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#808080")
        textSize = 16f * resources.displayMetrics.density
        textAlign = Paint.Align.CENTER
    }
    
    private val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 18f * resources.displayMetrics.density
        textAlign = Paint.Align.CENTER
        typeface = android.graphics.Typeface.DEFAULT_BOLD
    }
    
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF6B35")
        textSize = 18f * resources.displayMetrics.density
        textAlign = Paint.Align.CENTER
        typeface = android.graphics.Typeface.DEFAULT_BOLD
    }
    
    private var lyrics: List<LyricLine> = emptyList()
    private var currentIndex = -1
    private var currentProgress = 0f
    
    data class LyricLine(
        val text: String,
        val startTime: Long,
        val endTime: Long
    )
    
    fun setLyrics(lyrics: List<LyricLine>) {
        this.lyrics = lyrics
        this.currentIndex = -1
        invalidate()
    }
    
    fun setCurrentTime(time: Long) {
        var found = false
        for (i in lyrics.indices) {
            val line = lyrics[i]
            if (time >= line.startTime && time < line.endTime) {
                if (currentIndex != i) {
                    currentIndex = i
                    currentProgress = (time - line.startTime).toFloat() / (line.endTime - line.startTime)
                    found = true
                    break
                } else {
                    currentProgress = (time - line.startTime).toFloat() / (line.endTime - line.startTime)
                    found = true
                }
            }
        }
        
        if (found) {
            invalidate()
        }
    }
    
    override fun onDraw(canvas: Canvas) {
        if (lyrics.isEmpty()) {
            super.onDraw(canvas)
            return
        }
        
        val centerY = height / 2f
        val centerX = width / 2f
        val lineHeight = 60f * resources.displayMetrics.density
        
        // 绘制前后歌词
        for (i in lyrics.indices) {
            val offset = (i - currentIndex) * lineHeight
            val y = centerY + offset
            
            if (y < -lineHeight || y > height + lineHeight) continue
            
            when {
                i == currentIndex -> {
                    // 当前行 - 高亮
                    canvas.drawText(lyrics[i].text, centerX, y, highlightPaint)
                }
                abs(i - currentIndex) <= 2 -> {
                    // 附近的行 - 半透明
                    val alpha = (255 * (1 - abs(i - currentIndex) * 0.3f)).toInt().coerceIn(0, 255)
                    normalPaint.alpha = alpha
                    canvas.drawText(lyrics[i].text, centerX, y, normalPaint)
                }
            }
        }
    }
}