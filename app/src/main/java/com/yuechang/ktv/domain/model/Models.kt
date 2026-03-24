package com.yuechang.ktv.domain.model

/**
 * 歌曲领域模型
 */
data class Song(
    val songId: String,
    val name: String,
    val artist: String,
    val album: String?,
    val coverUrl: String?,
    val audioUrl: String?,
    val videoUrl: String?,
    val lyricsUrl: String?,
    val accompanyUrl: String?,
    val duration: Long,
    val categoryId: String?,
    val tags: List<String>,
    val vipRequired: Boolean,
    val playCount: Long
) {
    val isFree: Boolean get() = !vipRequired
    val hasVideo: Boolean get() = !videoUrl.isNullOrEmpty()
    val durationFormatted: String
        get() {
            val seconds = duration / 1000
            val minutes = seconds / 60
            val secs = seconds % 60
            return String.format("%02d:%02d", minutes, secs)
        }
}

/**
 * 用户领域模型
 */
data class User(
    val userId: String,
    val nickname: String,
    val avatar: String?,
    val vipLevel: Int,
    val vipExpireTime: Long,
    val coins: Long,
    val beans: Long
) {
    val isVip: Boolean
        get() = vipLevel > 0 && vipExpireTime > System.currentTimeMillis()
    
    val vipType: VipType
        get() = when (vipLevel) {
            1 -> VipType.MONTHLY
            2 -> VipType.QUARTERLY
            3 -> VipType.YEARLY
            else -> VipType.NONE
        }
}

enum class VipType {
    NONE, MONTHLY, QUARTERLY, YEARLY
}

/**
 * 作品领域模型
 */
data class Work(
    val workId: String,
    val userId: String,
    val songId: String,
    val songName: String,
    val artist: String,
    val coverUrl: String?,
    val audioPath: String,
    val videoPath: String?,
    val duration: Long,
    val score: Int,
    val playCount: Long,
    val likeCount: Long,
    val isPublic: Boolean
) {
    val scoreLevel: ScoreLevel
        get() = when {
            score >= 90 -> ScoreLevel.SSS
            score >= 80 -> ScoreLevel.SS
            score >= 70 -> ScoreLevel.S
            score >= 60 -> ScoreLevel.A
            else -> ScoreLevel.B
        }
}

enum class ScoreLevel {
    SSS, SS, S, A, B
}

/**
 * 歌词行
 */
data class LyricLine(
    val time: Long,
    val duration: Long,
    val text: String,
    val pitch: Int?
)

/**
 * 录音配置
 */
data class RecordConfig(
    val sampleRate: Int = 44100,
    val channels: Int = 1,
    val bitrate: Int = 128000,
    val format: AudioFormat = AudioFormat.MP3,
    val enableEarMonitor: Boolean = true,  // 耳返
    val enablePitchCorrection: Boolean = false // 修音
)

enum class AudioFormat {
    MP3, AAC, WAV
}