package com.yuechang.ktv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户实体
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val nickname: String,
    val avatar: String? = null,
    val phone: String? = null,
    val gender: Int = 0,                 // 0未知 1男 2女
    val signature: String? = null,       // 个性签名
    val vipLevel: Int = 0,               // VIP等级 0普通 1月卡 2季卡 3年卡
    val vipExpireTime: Long = 0,         // VIP过期时间
    val coins: Long = 0,                 // 唱币
    val beans: Long = 0,                 // 唱豆
    val fansCount: Long = 0,             // 粉丝数
    val followCount: Long = 0,           // 关注数
    val workCount: Long = 0,             // 作品数
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)