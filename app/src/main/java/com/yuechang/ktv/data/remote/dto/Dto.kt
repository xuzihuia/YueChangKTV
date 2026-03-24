package com.yuechang.ktv.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * 通用API响应
 */
data class ApiResponse<T>(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T?
) {
    val isSuccess: Boolean get() = code == 0
}

/**
 * 歌曲DTO
 */
data class SongDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("artistId") val artistId: String?,
    @SerializedName("album") val album: String?,
    @SerializedName("coverUrl") val coverUrl: String?,
    @SerializedName("duration") val duration: Long,
    @SerializedName("isVip") val isVip: Boolean,
    @SerializedName("playCount") val playCount: Long,
    @SerializedName("tags") val tags: List<String>?
)

/**
 * 歌曲详情DTO
 */
data class SongDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("artistId") val artistId: String?,
    @SerializedName("album") val album: String?,
    @SerializedName("coverUrl") val coverUrl: String?,
    @SerializedName("videoUrl") val videoUrl: String?,
    @SerializedName("audioUrl") val audioUrl: String?,
    @SerializedName("accompanyUrl") val accompanyUrl: String?,
    @SerializedName("lyricsUrl") val lyricsUrl: String?,
    @SerializedName("pitchDataUrl") val pitchDataUrl: String?,
    @SerializedName("duration") val duration: Long,
    @SerializedName("isVip") val isVip: Boolean,
    @SerializedName("playCount") val playCount: Long
)

/**
 * 歌词DTO
 */
data class LyricsDto(
    @SerializedName("songId") val songId: String,
    @SerializedName("lines") val lines: List<LyricLineDto>
)

data class LyricLineDto(
    @SerializedName("startTime") val startTime: Long,
    @SerializedName("duration") val duration: Long,
    @SerializedName("text") val text: String,
    @SerializedName("pitch") val pitch: Int?
)

/**
 * 音高数据DTO
 */
data class PitchPointDto(
    @SerializedName("time") val time: Long,
    @SerializedName("pitch") val pitch: Int,
    @SerializedName("duration") val duration: Long
)

/**
 * 搜索结果DTO
 */
data class SearchResultDto(
    @SerializedName("songs") val songs: List<SongDto>?,
    @SerializedName("artists") val artists: List<ArtistDto>?
)

/**
 * 分类DTO
 */
data class CategoryDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("icon") val icon: String?,
    @SerializedName("backgroundImage") val backgroundImage: String?,
    @SerializedName("sortOrder") val sortOrder: Int,
    @SerializedName("songCount") val songCount: Int
)

/**
 * 歌手DTO
 */
data class ArtistDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("songCount") val songCount: Int,
    @SerializedName("isHot") val isHot: Boolean
)

data class ArtistDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("songCount") val songCount: Int,
    @SerializedName("playCount") val playCount: Long
)

/**
 * 用户DTO
 */
data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("phone") val phone: String?,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("gender") val gender: Int,
    @SerializedName("signature") val signature: String?,
    @SerializedName("isVip") val isVip: Boolean,
    @SerializedName("vipLevel") val vipLevel: Int,
    @SerializedName("vipExpireTime") val vipExpireTime: Long,
    @SerializedName("coins") val coins: Long,
    @SerializedName("beans") val beans: Long,
    @SerializedName("fansCount") val fansCount: Long,
    @SerializedName("followCount") val followCount: Long,
    @SerializedName("workCount") val workCount: Long
)

/**
 * 作品DTO
 */
data class WorkDto(
    @SerializedName("id") val id: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("songId") val songId: String,
    @SerializedName("songName") val songName: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("coverUrl") val coverUrl: String?,
    @SerializedName("duration") val duration: Long,
    @SerializedName("score") val score: Int,
    @SerializedName("scoreLevel") val scoreLevel: String,
    @SerializedName("playCount") val playCount: Long,
    @SerializedName("likeCount") val likeCount: Long,
    @SerializedName("createTime") val createTime: Long
)

data class WorkDetailDto(
    @SerializedName("id") val id: String,
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
    @SerializedName("scoreLevel") val scoreLevel: String,
    @SerializedName("playCount") val playCount: Long,
    @SerializedName("likeCount") val likeCount: Long,
    @SerializedName("commentCount") val commentCount: Long,
    @SerializedName("shareCount") val shareCount: Long,
    @SerializedName("isLiked") val isLiked: Boolean,
    @SerializedName("createTime") val createTime: Long
)

/**
 * Banner DTO
 */
data class BannerDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("targetUrl") val targetUrl: String?,
    @SerializedName("targetType") val targetType: Int,
    @SerializedName("targetId") val targetId: String?,
    @SerializedName("sortOrder") val sortOrder: Int
)

/**
 * VIP信息DTO
 */
data class VipInfoDto(
    @SerializedName("level") val level: Int,
    @SerializedName("expireTime") val expireTime: Long,
    @SerializedName("benefits") val benefits: List<VipBenefitDto>
)

data class VipBenefitDto(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String?
)

/**
 * 登录请求/响应
 */
data class PhoneLoginRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String
)

data class PasswordLoginRequest(
    @SerializedName("account") val account: String,
    @SerializedName("password") val password: String
)

data class SmsLoginRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("code") val code: String
)

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("expireTime") val expireTime: Long,
    @SerializedName("user") val user: UserDto
)

data class UpdateProfileRequest(
    @SerializedName("nickname") val nickname: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("signature") val signature: String?
)

data class UploadWorkRequest(
    @SerializedName("songId") val songId: String,
    @SerializedName("audioPath") val audioPath: String?,
    @SerializedName("videoPath") val videoPath: String?,
    @SerializedName("score") val score: Int,
    @SerializedName("isPublic") val isPublic: Boolean
)

data class FavoriteResultDto(
    @SerializedName("isFavorite") val isFavorite: Boolean,
    @SerializedName("favoriteCount") val favoriteCount: Long
)

data class LikeResultDto(
    @SerializedName("isLiked") val isLiked: Boolean,
    @SerializedName("likeCount") val likeCount: Long
)

data class ShareResultDto(
    @SerializedName("shareUrl") val shareUrl: String,
    @SerializedName("shareCount") val shareCount: Long
)