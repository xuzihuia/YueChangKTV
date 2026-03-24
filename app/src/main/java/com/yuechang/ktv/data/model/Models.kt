package com.yuechang.ktv.data.model

data class Song(
    val id: String,
    val name: String,
    val artist: String,
    val cover: String = "",
    val duration: Long = 0,
    val isVip: Boolean = false,
    val playCount: Long = 0
)

data class Category(
    val id: String,
    val name: String,
    val icon: String = ""
)

data class User(
    val id: String,
    val nickname: String,
    val avatar: String = "",
    val isVip: Boolean = false
)

data class Work(
    val id: String,
    val songName: String,
    val score: Int,
    val createTime: Long = System.currentTimeMillis()
)