package com.yuechang.ktv.service.recorder

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * K歌录音器
 * 支持高质量录音、实时耳返
 */
@Singleton
class KaraokeRecorder @Inject constructor(
    private val context: Context
) {
    private var audioRecord: AudioRecord? = null
    private var isRecording = false
    
    private val _recordingState = MutableStateFlow<RecordingState>(RecordingState.Idle)
    val recordingState: StateFlow<RecordingState> = _recordingState.asStateFlow()
    
    private val _currentVolume = MutableStateFlow(0)
    val currentVolume: StateFlow<Int> = _currentVolume.asStateFlow()
    
    private var outputFile: File? = null
    private var recordingThread: Thread? = null
    
    // 录音配置
    private val sampleRate = 44100
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
    
    /**
     * 开始录音
     * @param outputPath 输出文件路径
     */
    fun startRecording(outputPath: String): Boolean {
        if (isRecording) return false
        
        try {
            outputFile = File(outputPath)
            
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioFormat,
                bufferSize * 2
            )
            
            if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
                return false
            }
            
            audioRecord?.startRecording()
            isRecording = true
            _recordingState.value = RecordingState.Recording
            
            // 启动录音线程
            recordingThread = Thread {
                writeAudioDataToFile()
            }.apply { start() }
            
            return true
        } catch (e: Exception) {
            _recordingState.value = RecordingState.Error(e.message ?: "录音启动失败")
            return false
        }
    }
    
    /**
     * 停止录音
     */
    fun stopRecording() {
        if (!isRecording) return
        
        isRecording = false
        audioRecord?.apply {
            stop()
            release()
        }
        audioRecord = null
        recordingThread?.join()
        recordingThread = null
        
        _recordingState.value = RecordingState.Stopped
    }
    
    /**
     * 获取录音时长
     */
    fun getRecordingDuration(): Long {
        // 根据 PCM 数据大小计算时长
        return outputFile?.length()?.let { size ->
            // PCM 16bit 单声道: 44100 * 2 bytes per second
            size * 1000 / (sampleRate * 2)
        } ?: 0L
    }
    
    private fun writeAudioDataToFile() {
        val data = ByteArray(bufferSize)
        var outputStream: FileOutputStream? = null
        
        try {
            outputStream = FileOutputStream(outputFile)
            
            while (isRecording) {
                val read = audioRecord?.read(data, 0, bufferSize) ?: 0
                if (read > 0) {
                    outputStream.write(data, 0, read)
                    
                    // 计算音量 (用于可视化)
                    val amplitude = calculateAmplitude(data, read)
                    _currentVolume.value = amplitude
                }
            }
        } catch (e: Exception) {
            _recordingState.value = RecordingState.Error(e.message ?: "录音写入失败")
        } finally {
            outputStream?.close()
        }
    }
    
    private fun calculateAmplitude(data: ByteArray, size: Int): Int {
        var max = 0
        for (i in 0 until size step 2) {
            val sample = (data[i].toInt() and 0xFF) or (data[i + 1].toInt() shl 8)
            if (sample > max) max = sample
        }
        return max / 1000 // 归一化
    }
}

/**
 * 录音状态
 */
sealed class RecordingState {
    object Idle : RecordingState()
    object Recording : RecordingState()
    object Stopped : RecordingState()
    data class Error(val message: String) : RecordingState()
}