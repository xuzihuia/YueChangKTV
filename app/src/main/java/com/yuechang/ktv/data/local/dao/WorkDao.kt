package com.yuechang.ktv.data.local.dao

import androidx.room.*
import com.yuechang.ktv.data.local.entity.WorkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDao {
    
    @Query("SELECT * FROM works WHERE workId = :workId")
    suspend fun getWorkById(workId: String): WorkEntity?
    
    @Query("SELECT * FROM works WHERE userId = :userId ORDER BY createdAt DESC")
    fun getWorksByUser(userId: String): Flow<List<WorkEntity>>
    
    @Query("SELECT * FROM works WHERE userId = :userId AND isPublic = 1 ORDER BY playCount DESC LIMIT :limit")
    fun getPopularWorks(userId: String, limit: Int = 20): Flow<List<WorkEntity>>
    
    @Query("SELECT * FROM works ORDER BY createdAt DESC LIMIT :limit")
    fun getLatestWorks(limit: Int = 50): Flow<List<WorkEntity>>
    
    @Query("SELECT * FROM works ORDER BY playCount DESC LIMIT :limit")
    fun getHotWorks(limit: Int = 50): Flow<List<WorkEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWork(work: WorkEntity)
    
    @Update
    suspend fun updateWork(work: WorkEntity)
    
    @Query("UPDATE works SET playCount = playCount + 1 WHERE workId = :workId")
    suspend fun incrementPlayCount(workId: String)
    
    @Query("UPDATE works SET likeCount = likeCount + 1 WHERE workId = :workId")
    suspend fun incrementLikeCount(workId: String)
    
    @Delete
    suspend fun deleteWork(work: WorkEntity)
    
    @Query("DELETE FROM works WHERE userId = :userId")
    suspend fun deleteWorksByUser(userId: String)
}