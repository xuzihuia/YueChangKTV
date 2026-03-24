package com.yuechang.ktv.data.local.entity

data class CategoryEntity(
    val categoryId: String,
    val name: String,
    val icon: String? = null,
    val sortOrder: Int = 0
)