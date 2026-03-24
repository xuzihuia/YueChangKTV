package com.yuechang.ktv.data.local.dao

import androidx.room.*
import com.yuechang.ktv.data.local.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    
    @Query("SELECT * FROM songs WHERE songId = :songId")
    suspend fun getSongById(songId: String): SongEntity?
    
    @Query("SELECT * FROM songs WHERE categoryId = :categoryId ORDER BY playCount DESC")
    fun getSongsByCategory(categoryId: String): Flow<List<SongEntity>>
    
    @Query("SELECT * FROM songs WHERE tags LIKE '%' || :tag || '%' ORDER BY playCount DESC")
    fun getSongsByTag(tag: String): Flow<List<SongEntity>>
    
    @Query("SELECT * FROM songs WHERE name LIKE '%' || :keyword || '%' OR artist LIKE '%' || :keyword || '%'")
    fun searchSongs(keyword: String): Flow<List<SongEntity>>
    
    @Query("SELECT * FROM songs ORDER BY playCount DESC LIMIT :limit")
    fun getHotSongs(limit: Int = 50): Flow<List<SongEntity>>
    
    @Query("SELECT * FROM songs ORDER BY createdAt DESC LIMIT :limit")
    fun getNewSongs(limit: Int = 50): Flow<List<SongEntity>>
    
    @Query("SELECT * FROM songs WHERE vipRequired = 0 ORDER BY playCount DESC LIMIT :limit")
    fun getFreeSongs(limit: Int = 50): Flow<List<SongEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: SongEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<SongEntity>)
    
    @Update
    suspend fun updateSong(song: SongEntity)
    
    @Delete
    suspend fun deleteSong(song: SongEntity)
    
    @Query("DELETE FROM songs")
    suspend fun deleteAllSongs()
}