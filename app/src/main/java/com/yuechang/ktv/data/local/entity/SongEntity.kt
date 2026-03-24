package com.yuechang.ktv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 歌曲实体
 * 对标唱享K歌歌曲数据结构
 */
@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey
    val songId: String,
    val name: String,                    // 歌曲名
    val artist: String,                  // 歌手
    val album: String? = null,           // 专辑
    val coverUrl: String? = null,        // 封面URL
    val audioUrl: String? = null,        // 音频URL
    val videoUrl: String? = null,        // 视频URL (视频K歌)
    val lyricsUrl: String? = null,       // 歌词URL
    val accompanyUrl: String? = null,    // 伴奏URL
    val duration: Long = 0,              // 时长(毫秒)
    val categoryId: String? = null,      // 分类ID
    val tags: List<String> = emptyList(),// 标签(热门、新歌等)
    val vipRequired: Boolean = false,    // 是否需要VIP
    val playCount: Long = 0,             // 播放量
    val favoriteCount: Long = 0,         // 收藏量
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)