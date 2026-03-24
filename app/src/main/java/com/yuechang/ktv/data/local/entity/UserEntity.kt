package com.yuechang.ktv.data.local.entity

data class UserEntity(
    val userId: String,
    val nickname: String,
    val avatar: String? = null,
    val vipLevel: Int = 0
)