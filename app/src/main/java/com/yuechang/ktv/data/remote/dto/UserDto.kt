package com.yuechang.ktv.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * 用户 DTO
 */
data class UserDto(
    @SerializedName("userId") val userId: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("gender") val gender: Int,
    @SerializedName("signature") val signature: String?,
    @SerializedName("vipLevel") val vipLevel: Int,
    @SerializedName("vipExpireTime") val vipExpireTime: Long,
    @SerializedName("coins") val coins: Long,
    @SerializedName("beans") val beans: Long,
    @SerializedName("fansCount") val fansCount: Long,
    @SerializedName("followCount") val followCount: Long,
    @SerializedName("workCount") val workCount: Long
)

/**
 * 登录请求
 */
data class LoginRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String? = null,
    @SerializedName("code") val code: String? = null
)

/**
 * 短信登录请求
 */
data class SmsLoginRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("code") val code: String
)

/**
 * 登录响应
 */
data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("expireTime") val expireTime: Long,
    @SerializedName("user") val user: UserDto
)

/**
 * 更新资料请求
 */
data class UpdateProfileRequest(
    @SerializedName("nickname") val nickname: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("gender") val gender: Int? = null,
    @SerializedName("signature") val signature: String? = null
)

/**
 * VIP 信息
 */
data class VipInfoDto(
    @SerializedName("level") val level: Int,
    @SerializedName("expireTime") val expireTime: Long,
    @SerializedName("benefits") val benefits: List<VipBenefit>
)

data class VipBenefit(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String?
)

/**
 * VIP 购买请求
 */
data class PurchaseVipRequest(
    @SerializedName("type") val type: Int,  // 1月卡 2季卡 3年卡
    @SerializedName("payType") val payType: String  // wechat/alipay
)

/**
 * VIP 购买结果
 */
data class VipPurchaseResultDto(
    @SerializedName("orderId") val orderId: String,
    @SerializedName("payUrl") val payUrl: String?,
    @SerializedName("qrCode") val qrCode: String?
)