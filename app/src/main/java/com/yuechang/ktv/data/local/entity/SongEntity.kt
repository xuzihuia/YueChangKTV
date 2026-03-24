package com.yuechang.ktv.data.local.entity

data class SongEntity(
    val songId: String,
    val name: String,
    val artist: String,
    val album: String? = null,
    val coverUrl: String? = null,
    val duration: Long = 0,
    val vipRequired: Boolean = false,
    val playCount: Long = 0
)