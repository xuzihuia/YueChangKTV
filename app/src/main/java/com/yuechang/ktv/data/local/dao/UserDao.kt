package com.yuechang.ktv.data.local.dao

import androidx.room.*
import com.yuechang.ktv.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: String): UserEntity?
    
    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserFlow(userId: String): Flow<UserEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Query("UPDATE users SET vipLevel = :level, vipExpireTime = :expireTime WHERE userId = :userId")
    suspend fun updateVip(userId: String, level: Int, expireTime: Long)
    
    @Query("UPDATE users SET coins = coins + :amount WHERE userId = :userId")
    suspend fun addCoins(userId: String, amount: Long)
    
    @Query("UPDATE users SET beans = beans + :amount WHERE userId = :userId")
    suspend fun addBeans(userId: String, amount: Long)
    
    @Delete
    suspend fun deleteUser(user: UserEntity)
}