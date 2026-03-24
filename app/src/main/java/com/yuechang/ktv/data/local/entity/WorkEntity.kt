package com.yuechang.ktv.data.local.entity

data class WorkEntity(
    val workId: String,
    val userId: String,
    val songId: String,
    val songName: String,
    val score: Int = 0
)