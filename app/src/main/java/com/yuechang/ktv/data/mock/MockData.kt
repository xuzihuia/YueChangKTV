package com.yuechang.ktv.data.mock

import com.yuechang.ktv.data.local.entity.SongEntity
import com.yuechang.ktv.data.local.entity.CategoryEntity
import com.yuechang.ktv.data.local.entity.Categories

/**
 * Mock 数据 - 用于开发和测试
 */
object MockData {
    
    fun getMockSongs(): List<SongEntity> = listOf(
        SongEntity(
            songId = "song_001",
            name = "起风了",
            artist = "买辣椒也用券",
            album = "起风了",
            coverUrl = "https://example.com/covers/1.jpg",
            audioUrl = "https://example.com/audio/1.mp3",
            videoUrl = "https://example.com/video/1.mp4",
            duration = 325000,
            categoryId = Categories.HOT,
            tags = listOf("热门", "流行"),
            vipRequired = false,
            playCount = 1500000
        ),
        SongEntity(
            songId = "song_002",
            name = "晴天",
            artist = "周杰伦",
            album = "叶惠美",
            coverUrl = "https://example.com/covers/2.jpg",
            audioUrl = "https://example.com/audio/2.mp3",
            duration = 269000,
            categoryId = Categories.CLASSIC,
            tags = listOf("经典", "华语"),
            vipRequired = false,
            playCount = 5000000
        ),
        SongEntity(
            songId = "song_003",
            name = "孤勇者",
            artist = "陈奕迅",
            album = "孤勇者",
            coverUrl = "https://example.com/covers/3.jpg",
            audioUrl = "https://example.com/audio/3.mp3",
            duration = 256000,
            categoryId = Categories.HOT,
            tags = listOf("热门", "影视"),
            vipRequired = true,
            playCount = 8000000
        ),
        SongEntity(
            songId = "song_004",
            name = "稻香",
            artist = "周杰伦",
            album = "魔杰座",
            coverUrl = "https://example.com/covers/4.jpg",
            audioUrl = "https://example.com/audio/4.mp3",
            duration = 223000,
            categoryId = Categories.CLASSIC,
            tags = listOf("经典", "治愈"),
            vipRequired = false,
            playCount = 3000000
        ),
        SongEntity(
            songId = "song_005",
            name = "光年之外",
            artist = "邓紫棋",
            album = "光年之外",
            coverUrl = "https://example.com/covers/5.jpg",
            audioUrl = "https://example.com/audio/5.mp3",
            duration = 235000,
            categoryId = Categories.HOT,
            tags = listOf("热门", "影视"),
            vipRequired = true,
            playCount = 6000000
        ),
        SongEntity(
            songId = "song_006",
            name = "海阔天空",
            artist = "Beyond",
            album = "乐与怒",
            coverUrl = "https://example.com/covers/6.jpg",
            audioUrl = "https://example.com/audio/6.mp3",
            duration = 326000,
            categoryId = Categories.CLASSIC,
            tags = listOf("经典", "粤语"),
            vipRequired = false,
            playCount = 10000000
        ),
        SongEntity(
            songId = "song_007",
            name = "小星星",
            artist = "儿童歌曲",
            album = "儿歌精选",
            coverUrl = "https://example.com/covers/7.jpg",
            audioUrl = "https://example.com/audio/7.mp3",
            duration = 120000,
            categoryId = Categories.CHILDREN,
            tags = listOf("儿童", "儿歌"),
            vipRequired = false,
            playCount = 2000000
        ),
        SongEntity(
            songId = "song_008",
            name = "东方红",
            artist = "合唱团",
            album = "经典红歌",
            coverUrl = "https://example.com/covers/8.jpg",
            audioUrl = "https://example.com/audio/8.mp3",
            duration = 180000,
            categoryId = Categories.ELDER,
            tags = listOf("长辈", "红歌"),
            vipRequired = false,
            playCount = 1500000
        )
    )
    
    fun getMockCategories(): List<CategoryEntity> = listOf(
        CategoryEntity(Categories.HOT, "热门推荐", sortOrder = 1),
        CategoryEntity(Categories.NEW, "新歌速递", sortOrder = 2),
        CategoryEntity(Categories.CLASSIC, "经典老歌", sortOrder = 3),
        CategoryEntity(Categories.CHINESE, "华语金曲", sortOrder = 4),
        CategoryEntity(Categories.WESTERN, "欧美流行", sortOrder = 5),
        CategoryEntity(Categories.KOREAN, "日韩音乐", sortOrder = 6),
        CategoryEntity(Categories.CHILDREN, "儿童专区", sortOrder = 7),
        CategoryEntity(Categories.ELDER, "长辈专区", sortOrder = 8),
        CategoryEntity(Categories.FREE, "免费专区", sortOrder = 9),
        CategoryEntity(Categories.VARIETY, "综艺歌曲", sortOrder = 10)
    )
    
    fun getHotSearchKeywords(): List<String> = listOf(
        "起风了", "孤勇者", "晴天", "稻香", "光年之外",
        "海阔天空", "七里香", "夜曲", "青花瓷", "告白气球"
    )
}