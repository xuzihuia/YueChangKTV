package com.yuechang.ktv.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * 作品 DTO
 */
data class WorkDto(
    @SerializedName("workId") val workId: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("songId") val songId: String,
    @SerializedName("songName") val songName: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("coverUrl") val coverUrl: String?,
    @SerializedName("audioUrl") val audioUrl: String?,
    @SerializedName("videoUrl") val videoUrl: String?,
    @SerializedName("duration") val duration: Long,
    @SerializedName("score") val score: Int,
    @SerializedName("playCount") val playCount: Long,
    @SerializedName("likeCount") val likeCount: Long,
    @SerializedName("commentCount") val commentCount: Long,
    @SerializedName("createdAt") val createdAt: Long
)

/**
 * 作品详情
 */
data class WorkDetailDto(
    @SerializedName("workId") val workId: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("songId") val songId: String,
    @SerializedName("songName") val songName: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("coverUrl") val coverUrl: String?,
    @SerializedName("audioUrl") val audioUrl: String?,
    @SerializedName("videoUrl") val videoUrl: String?,
    @SerializedName("duration") val duration: Long,
    @SerializedName("score") val score: Int,
    @SerializedName("playCount") val playCount: Long,
    @SerializedName("likeCount") val likeCount: Long,
    @SerializedName("commentCount") val commentCount: Long,
    @SerializedName("shareCount") val shareCount: Long,
    @SerializedName("isLiked") val isLiked: Boolean,
    @SerializedName("lyrics") val lyrics: List<LyricLine>?,
    @SerializedName("comments") val comments: List<CommentDto>?,
    @SerializedName("createdAt") val createdAt: Long
)

/**
 * 评论 DTO
 */
data class CommentDto(
    @SerializedName("commentId") val commentId: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("content") val content: String,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("createdAt") val createdAt: Long
)

/**
 * 上传作品请求
 */
data class UploadWorkRequest(
    @SerializedName("songId") val songId: String,
    @SerializedName("audioPath") val audioPath: String?,
    @SerializedName("videoPath") val videoPath: String?,
    @SerializedName("score") val score: Int,
    @SerializedName("isPublic") val isPublic: Boolean
)

/**
 * 点赞结果
 */
data class LikeResultDto(
    @SerializedName("isLiked") val isLiked: Boolean,
    @SerializedName("likeCount") val likeCount: Int
)

/**
 * 分享结果
 */
data class ShareResultDto(
    @SerializedName("shareUrl") val shareUrl: String,
    @SerializedName("shareCount") val shareCount: Int
)