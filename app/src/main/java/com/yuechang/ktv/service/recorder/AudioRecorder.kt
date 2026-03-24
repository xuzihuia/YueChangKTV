package com.yuechang.ktv.service.recorder

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * 音频录制器
 * 支持高质量录音
 */
class AudioRecorder {
    
    companion object {
        private const val TAG = "AudioRecorder"
        private const val SAMPLE_RATE = 44100
        private const val CHANNEL = AudioFormat.CHANNEL_IN_MONO
        private const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    }
    
    private var audioRecord: AudioRecord? = null
    private var recordingThread: Thread? = null
    private var isRecording = false
    
    private var outputFile: File? = null
    
    var onVolumeChanged: ((Int) -> Unit)? = null
    var onRecordingComplete: ((String) -> Unit)? = null
    var onError: ((String) -> Unit)? = null
    
    private val bufferSize: Int by lazy {
        AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL, AUDIO_FORMAT)
    }
    
    /**
     * 开始录音
     * @param outputPath 输出文件路径
     * @return 是否启动成功
     */
    fun startRecording(outputPath: String): Boolean {
        if (isRecording) {
            Log.w(TAG, "Already recording")
            return false
        }
        
        try {
            outputFile = File(outputPath)
            
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                CHANNEL,
                AUDIO_FORMAT,
                bufferSize * 2
            )
            
            if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
                onError?.invoke("录音器初始化失败")
                return false
            }
            
            audioRecord?.startRecording()
            isRecording = true
            
            recordingThread = Thread {
                writeAudioData()
            }.apply { start() }
            
            Log.d(TAG, "Recording started: $outputPath")
            return true
            
        } catch (e: SecurityException) {
            onError?.invoke("缺少录音权限")
            return false
        } catch (e: IOException) {
            onError?.invoke("录音启动失败: ${e.message}")
            return false
        }
    }
    
    /**
     * 停止录音
     */
    fun stopRecording() {
        if (!isRecording) return
        
        isRecording = false
        
        try {
            audioRecord?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping recording", e)
        }
        
        audioRecord = null
        
        recordingThread?.join(1000)
        recordingThread = null
        
        outputFile?.absolutePath?.let {
            onRecordingComplete?.invoke(it)
        }
        
        Log.d(TAG, "Recording stopped")
    }
    
    /**
     * 是否正在录音
     */
    fun isRecording(): Boolean = isRecording
    
    /**
     * 获取录音时长（毫秒）
     */
    fun getRecordingDuration(): Long {
        val fileSize = outputFile?.length()?.toLong() ?: 0L
        // PCM 16bit 单声道: 44100 * 2 bytes per second
        return if (fileSize > 0) fileSize * 1000 / (SAMPLE_RATE * 2) else 0L
    }
    
    /**
     * 释放资源
     */
    fun release() {
        stopRecording()
    }
    
    private fun writeAudioData() {
        val data = ByteArray(bufferSize)
        var outputStream: FileOutputStream? = null
        
        try {
            outputStream = FileOutputStream(outputFile)
            
            while (isRecording && audioRecord != null) {
                val read = audioRecord?.read(data, 0, bufferSize) ?: 0
                
                if (read > 0) {
                    outputStream.write(data, 0, read)
                    
                    // 计算音量
                    val amplitude = calculateAmplitude(data, read)
                    onVolumeChanged?.invoke(amplitude)
                }
            }
            
        } catch (e: IOException) {
            Log.e(TAG, "Error writing audio data", e)
            onError?.invoke("录音写入失败")
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Error closing output stream", e)
            }
        }
    }
    
    private fun calculateAmplitude(data: ByteArray, size: Int): Int {
        var max = 0
        for (i in 0 until size step 2) {
            if (i + 1 < size) {
                val sample = (data[i].toInt() and 0xFF) or (data[i + 1].toInt() shl 8)
                if (sample > max) max = sample
            }
        }
        // 归一化到 0-100
        return (max / 327.67).toInt().coerceIn(0, 100)
    }
}