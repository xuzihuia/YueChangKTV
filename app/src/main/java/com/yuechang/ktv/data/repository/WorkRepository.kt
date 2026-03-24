package com.yuechang.ktv.data.repository

import com.yuechang.ktv.data.local.dao.WorkDao
import com.yuechang.ktv.data.local.entity.WorkEntity
import com.yuechang.ktv.data.remote.api.WorkApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkRepository @Inject constructor(
    private val workApi: WorkApi,
    private val workDao: WorkDao
) {
    // 获取用户作品
    fun getUserWorks(userId: String): Flow<List<WorkEntity>> = 
        workDao.getWorksByUser(userId)
    
    // 获取热门作品
    fun getHotWorks(): Flow<List<WorkEntity>> = workDao.getHotWorks()
    
    // 获取最新作品
    fun getLatestWorks(): Flow<List<WorkEntity>> = workDao.getLatestWorks()
    
    // 获取作品详情
    suspend fun getWorkDetail(workId: String): Result<WorkEntity> {
        return try {
            val localWork = workDao.getWorkById(workId)
            if (localWork != null) {
                Result.success(localWork)
            } else {
                val response = workApi.getWorkDetail(workId)
                if (response.isSuccess && response.data != null) {
                    val entity = WorkEntity(
                        workId = response.data.workId,
                        userId = response.data.userId,
                        songId = response.data.songId,
                        songName = response.data.songName,
                        artist = response.data.artist,
                        coverUrl = response.data.coverUrl,
                        audioPath = response.data.audioUrl ?: "",
                        videoPath = response.data.videoUrl,
                        duration = response.data.duration,
                        score = response.data.score,
                        playCount = response.data.playCount,
                        likeCount = response.data.likeCount,
                        commentCount = response.data.commentCount
                    )
                    workDao.insertWork(entity)
                    Result.success(entity)
                } else {
                    Result.failure(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 上传作品
    suspend fun uploadWork(
        songId: String,
        audioPath: String?,
        videoPath: String?,
        score: Int,
        isPublic: Boolean
    ): Result<WorkEntity> {
        return try {
            val response = workApi.uploadWork(
                com.yuechang.ktv.data.remote.dto.UploadWorkRequest(
                    songId = songId,
                    audioPath = audioPath,
                    videoPath = videoPath,
                    score = score,
                    isPublic = isPublic
                )
            )
            if (response.isSuccess && response.data != null) {
                val entity = WorkEntity(
                    workId = response.data.workId,
                    userId = response.data.userId,
                    songId = response.data.songId,
                    songName = response.data.songName,
                    artist = response.data.artist,
                    coverUrl = response.data.coverUrl,
                    audioPath = audioPath ?: "",
                    videoPath = videoPath,
                    duration = response.data.duration,
                    score = response.data.score
                )
                workDao.insertWork(entity)
                Result.success(entity)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 点赞
    suspend fun likeWork(workId: String): Result<Boolean> {
        return try {
            val response = workApi.likeWork(workId)
            if (response.isSuccess && response.data != null) {
                Result.success(response.data.isLiked)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 增加播放量
    suspend fun incrementPlayCount(workId: String) {
        workDao.incrementPlayCount(workId)
    }
    
    // 删除作品
    suspend fun deleteWork(workId: String): Result<Unit> {
        return try {
            val response = workApi.deleteWork(workId)
            if (response.isSuccess) {
                val work = workDao.getWorkById(workId)
                work?.let { workDao.deleteWork(it) }
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}