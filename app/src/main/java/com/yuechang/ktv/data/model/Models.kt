package com.yuechang.ktv.data.model

/**
 * 歌曲数据模型
 */
data class Song(
    val id: String,
    val name: String,
    val artist: String,
    val artistId: String? = null,
    val album: String? = null,
    val coverUrl: String? = null,
    val videoUrl: String? = null,          // MV视频
    val audioUrl: String? = null,          // 原唱音频
    val accompanyUrl: String? = null,      // 伴奏音频
    val lyricsUrl: String? = null,         // 歌词文件
    val pitchDataUrl: String? = null,      // 音高数据
    val duration: Long = 0,
    val categoryId: String? = null,
    val tags: List<String> = emptyList(),
    val isVip: Boolean = false,
    val playCount: Long = 0,
    val favoriteCount: Long = 0,
    val createTime: Long = 0
) {
    fun getDurationText(): String {
        val seconds = duration / 1000
        val min = seconds / 60
        val sec = seconds % 60
        return String.format("%02d:%02d", min, sec)
    }
}

/**
 * 歌手数据模型
 */
data class Artist(
    val id: String,
    val name: String,
    val avatar: String? = null,
    val songCount: Int = 0,
    val isHot: Boolean = false
)

/**
 * 分类数据模型
 */
data class Category(
    val id: String,
    val name: String,
    val icon: String? = null,
    val backgroundImage: String? = null,
    val sortOrder: Int = 0,
    val parentId: String? = null,
    val songCount: Int = 0
)

/**
 * 用户数据模型
 */
data class User(
    val id: String,
    val phone: String? = null,
    val nickname: String,
    val avatar: String? = null,
    val gender: Int = 0,
    val signature: String? = null,
    val isVip: Boolean = false,
    val vipLevel: Int = 0,
    val vipExpireTime: Long = 0,
    val coins: Long = 0,
    val beans: Long = 0,
    val fansCount: Long = 0,
    val followCount: Long = 0,
    val workCount: Long = 0,
    val createTime: Long = 0
)

/**
 * 作品数据模型
 */
data class Work(
    val id: String,
    val userId: String,
    val songId: String,
    val songName: String,
    val artist: String,
    val coverUrl: String? = null,
    val audioPath: String? = null,
    val videoPath: String? = null,
    val duration: Long = 0,
    val score: Int = 0,
    val scoreLevel: String = "C",
    val playCount: Long = 0,
    val likeCount: Long = 0,
    val commentCount: Long = 0,
    val shareCount: Long = 0,
    val isPublic: Boolean = true,
    val createTime: Long = 0
)

/**
 * Banner数据模型
 */
data class Banner(
    val id: String,
    val title: String,
    val imageUrl: String,
    val targetUrl: String? = null,
    val targetType: Int = 0,   // 0无跳转 1歌曲 2歌手 3活动 4H5
    val targetId: String? = null,
    val sortOrder: Int = 0
)

/**
 * 歌词行数据
 */
data class LyricLine(
    val startTime: Long,
    val duration: Long,
    val text: String,
    val pitch: Int? = null    // 该句音高
)

/**
 * 音高数据
 */
data class PitchPoint(
    val time: Long,
    val pitch: Int,
    val duration: Long
)

/**
 * 评分结果
 */
data class ScoreResult(
    val score: Int,
    val level: String,
    val pitchScore: Double = 0.0,
    val rhythmScore: Double = 0.0,
    val stabilityScore: Double = 0.0
)