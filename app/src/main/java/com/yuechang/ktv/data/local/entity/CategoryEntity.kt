package com.yuechang.ktv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 歌曲分类实体
 * 对标唱享K歌分类：热门、新歌、经典、华语、欧美、日韩、少儿、长辈等
 */
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val categoryId: String,
    val name: String,
    val icon: String? = null,
    val sortOrder: Int = 0,
    val parentId: String? = null,        // 父分类ID(支持多级)
    val isActive: Boolean = true
)

// 预定义分类
object Categories {
    const val HOT = "hot"               // 热门推荐
    const val NEW = "new"               // 新歌速递
    const val CLASSIC = "classic"       // 经典老歌
    const val CHINESE = "chinese"       // 华语
    const val WESTERN = "western"       // 欧美
    const val KOREAN = "korean"         // 日韩
    const val CHILDREN = "children"     // 儿童专区
    const val ELDER = "elder"           // 长辈专区
    const val FREE = "free"             // 免费专区
    const val VARIETY = "variety"       // 综艺歌曲
}