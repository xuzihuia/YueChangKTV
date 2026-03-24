package com.yuechang.ktv.data.repository

import com.yuechang.ktv.data.local.dao.SongDao
import com.yuechang.ktv.data.local.entity.SongEntity
import com.yuechang.ktv.data.remote.api.SongApi
import com.yuechang.ktv.data.remote.dto.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRepository @Inject constructor(
    private val songApi: SongApi,
    private val songDao: SongDao
) {
    // 获取热门歌曲
    fun getHotSongs(): Flow<List<SongEntity>> = songDao.getHotSongs()
    
    // 获取新歌
    fun getNewSongs(): Flow<List<SongEntity>> = songDao.getNewSongs()
    
    // 获取免费歌曲
    fun getFreeSongs(): Flow<List<SongEntity>> = songDao.getFreeSongs()
    
    // 按分类获取歌曲
    fun getSongsByCategory(categoryId: String): Flow<List<SongEntity>> = 
        songDao.getSongsByCategory(categoryId)
    
    // 搜索歌曲
    fun searchSongs(keyword: String): Flow<List<SongEntity>> = 
        songDao.searchSongs(keyword)
    
    // 获取歌曲详情
    suspend fun getSongDetail(songId: String): Result<SongEntity> {
        return try {
            val localSong = songDao.getSongById(songId)
            if (localSong != null) {
                Result.success(localSong)
            } else {
                val response = songApi.getSongDetail(songId)
                if (response.isSuccess && response.data != null) {
                    val entity = response.data.toEntity()
                    songDao.insertSong(entity)
                    Result.success(entity)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 刷新热门歌曲
    suspend fun refreshHotSongs(): Result<Unit> {
        return try {
            val response = songApi.getHotSongs()
            if (response.isSuccess && response.data != null) {
                songDao.insertSongs(response.data.map { it.toEntity() })
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 清除缓存
    suspend fun clearCache() {
        songDao.deleteAllSongs()
    }
}

// DTO 转 Entity 扩展函数
fun com.yuechang.ktv.data.remote.dto.SongDto.toEntity() = SongEntity(
    songId = songId,
    name = name,
    artist = artist,
    album = album,
    coverUrl = coverUrl,
    audioUrl = audioUrl,
    videoUrl = videoUrl,
    duration = duration,
    categoryId = categoryId,
    tags = tags ?: emptyList(),
    vipRequired = vipRequired,
    playCount = playCount
)