package com.yuechang.ktv.data.mock

import com.yuechang.ktv.data.local.entity.SongEntity
import com.yuechang.ktv.data.local.entity.CategoryEntity

object MockData {
    
    fun getMockSongs(): List<SongEntity> = listOf(
        SongEntity(
            songId = "song_001",
            name = "起风了",
            artist = "买辣椒也用券",
            album = "起风了",
            duration = 325000,
            playCount = 1500000
        ),
        SongEntity(
            songId = "song_002",
            name = "晴天",
            artist = "周杰伦",
            album = "叶惠美",
            duration = 269000,
            playCount = 5000000
        ),
        SongEntity(
            songId = "song_003",
            name = "孤勇者",
            artist = "陈奕迅",
            album = "孤勇者",
            duration = 256000,
            vipRequired = true,
            playCount = 8000000
        ),
        SongEntity(
            songId = "song_004",
            name = "稻香",
            artist = "周杰伦",
            album = "魔杰座",
            duration = 223000,
            playCount = 3000000
        ),
        SongEntity(
            songId = "song_005",
            name = "光年之外",
            artist = "邓紫棋",
            album = "光年之外",
            duration = 235000,
            vipRequired = true,
            playCount = 6000000
        )
    )
    
    fun getMockCategories(): List<CategoryEntity> = listOf(
        CategoryEntity(categoryId = "hot", name = "热门推荐", sortOrder = 1),
        CategoryEntity(categoryId = "new", name = "新歌速递", sortOrder = 2),
        CategoryEntity(categoryId = "classic", name = "经典老歌", sortOrder = 3),
        CategoryEntity(categoryId = "chinese", name = "华语金曲", sortOrder = 4),
        CategoryEntity(categoryId = "free", name = "免费专区", sortOrder = 5)
    )
}