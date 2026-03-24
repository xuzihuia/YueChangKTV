package com.yuechang.ktv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 作品实体 - 用户录制的歌曲
 */
@Entity(tableName = "works")
data class WorkEntity(
    @PrimaryKey
    val workId: String,
    val userId: String,
    val songId: String,
    val songName: String,
    val artist: String,
    val coverUrl: String?,
    val audioPath: String,               // 本地录音文件路径
    val videoPath: String? = null,       // 本地视频文件路径
    val duration: Long,
    val score: Int = 0,                  // 得分 0-100
    val playCount: Long = 0,
    val likeCount: Long = 0,
    val commentCount: Long = 0,
    val shareCount: Long = 0,
    val isPublic: Boolean = true,        // 是否公开
    val createdAt: Long = System.currentTimeMillis()
)