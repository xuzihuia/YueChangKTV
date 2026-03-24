package com.yuechang.ktv.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * 通用 API 响应
 */
data class ApiResponse<T>(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T?
) {
    val isSuccess: Boolean get() = code == 0
}

/**
 * 歌曲 DTO
 */
data class SongDto(
    @SerializedName("songId") val songId: String,
    @SerializedName("name") val name: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("album") val album: String?,
    @SerializedName("coverUrl") val coverUrl: String?,
    @SerializedName("audioUrl") val audioUrl: String?,
    @SerializedName("videoUrl") val videoUrl: String?,
    @SerializedName("duration") val duration: Long,
    @SerializedName("categoryId") val categoryId: String?,
    @SerializedName("tags") val tags: List<String>?,
    @SerializedName("vipRequired") val vipRequired: Boolean,
    @SerializedName("playCount") val playCount: Long
)

/**
 * 歌曲详情 DTO
 */
data class SongDetailDto(
    @SerializedName("songId") val songId: String,
    @SerializedName("name") val name: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("album") val album: String?,
    @SerializedName("coverUrl") val coverUrl: String?,
    @SerializedName("audioUrl") val audioUrl: String?,
    @SerializedName("videoUrl") val videoUrl: String?,
    @SerializedName("accompanyUrl") val accompanyUrl: String?,
    @SerializedName("lyricsUrl") val lyricsUrl: String?,
    @SerializedName("duration") val duration: Long,
    @SerializedName("pitchData") val pitchData: List<PitchData>?,
    @SerializedName("vipRequired") val vipRequired: Boolean,
    @SerializedName("price") val price: Int?
)

/**
 * 音高数据 (用于评分)
 */
data class PitchData(
    @SerializedName("time") val time: Long,      // 时间点(毫秒)
    @SerializedName("pitch") val pitch: Int,     // 音高值
    @SerializedName("duration") val duration: Long // 持续时间
)

/**
 * 歌词 DTO
 */
data class LyricsDto(
    @SerializedName("songId") val songId: String,
    @SerializedName("lines") val lines: List<LyricLine>
)

data class LyricLine(
    @SerializedName("time") val time: Long,      // 开始时间
    @SerializedName("duration") val duration: Long,
    @SerializedName("text") val text: String,
    @SerializedName("pitch") val pitch: Int?     // 该句音高
)