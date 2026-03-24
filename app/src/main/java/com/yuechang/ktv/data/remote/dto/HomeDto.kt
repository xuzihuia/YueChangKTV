package com.yuechang.ktv.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Banner DTO
 */
data class BannerDto(
    @SerializedName("bannerId") val bannerId: String,
    @SerializedName("title") val title: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("targetUrl") val targetUrl: String?,
    @SerializedName("type") val type: Int,  // 1歌曲 2活动 3H5
    @SerializedName("targetId") val targetId: String?,
    @SerializedName("sortOrder") val sortOrder: Int
)

/**
 * 分类 DTO
 */
data class CategoryDto(
    @SerializedName("categoryId") val categoryId: String,
    @SerializedName("name") val name: String,
    @SerializedName("icon") val icon: String?,
    @SerializedName("parentId") val parentId: String?,
    @SerializedName("sortOrder") val sortOrder: Int,
    @SerializedName("songCount") val songCount: Int
)

/**
 * 歌单 DTO
 */
data class PlaylistDto(
    @SerializedName("playlistId") val playlistId: String,
    @SerializedName("name") val name: String,
    @SerializedName("coverUrl") val coverUrl: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("songCount") val songCount: Int,
    @SerializedName("playCount") val playCount: Long,
    @SerializedName("creator") val creator: CreatorDto?
)

data class CreatorDto(
    @SerializedName("userId") val userId: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("avatar") val avatar: String?
)