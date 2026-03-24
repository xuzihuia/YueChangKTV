package com.yuechang.ktv.data.repository

import com.yuechang.ktv.data.local.dao.UserDao
import com.yuechang.ktv.data.local.entity.UserEntity
import com.yuechang.ktv.data.remote.api.UserApi
import com.yuechang.ktv.data.remote.dto.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao
) {
    // 获取当前用户
    fun getCurrentUser(userId: String): Flow<UserEntity?> = 
        userDao.getUserFlow(userId)
    
    // 登录
    suspend fun login(phone: String, password: String): Result<UserEntity> {
        return try {
            val response = userApi.login(
                com.yuechang.ktv.data.remote.dto.LoginRequest(phone, password)
            )
            if (response.isSuccess && response.data != null) {
                val entity = response.data.user.toEntity()
                userDao.insertUser(entity)
                // 保存 token
                saveToken(response.data.token, response.data.refreshToken)
                Result.success(entity)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 短信登录
    suspend fun loginBySms(phone: String, code: String): Result<UserEntity> {
        return try {
            val response = userApi.loginBySms(
                com.yuechang.ktv.data.remote.dto.SmsLoginRequest(phone, code)
            )
            if (response.isSuccess && response.data != null) {
                val entity = response.data.user.toEntity()
                userDao.insertUser(entity)
                saveToken(response.data.token, response.data.refreshToken)
                Result.success(entity)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 登出
    suspend fun logout(): Result<Unit> {
        return try {
            userApi.logout()
            clearToken()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 刷新用户信息
    suspend fun refreshProfile(): Result<UserEntity> {
        return try {
            val response = userApi.getProfile()
            if (response.isSuccess && response.data != null) {
                val entity = response.data.toEntity()
                userDao.insertUser(entity)
                Result.success(entity)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 更新 VIP
    suspend fun updateVip(userId: String, level: Int, expireTime: Long) {
        userDao.updateVip(userId, level, expireTime)
    }
    
    // 添加唱币
    suspend fun addCoins(userId: String, amount: Long) {
        userDao.addCoins(userId, amount)
    }
    
    private fun saveToken(token: String, refreshToken: String) {
        // TODO: 保存到 SharedPreferences 或 DataStore
    }
    
    private fun clearToken() {
        // TODO: 清除 token
    }
}

// DTO 转 Entity 扩展函数
fun com.yuechang.ktv.data.remote.dto.UserDto.toEntity() = UserEntity(
    userId = userId,
    nickname = nickname,
    avatar = avatar,
    phone = phone,
    gender = gender,
    signature = signature,
    vipLevel = vipLevel,
    vipExpireTime = vipExpireTime,
    coins = coins,
    beans = beans,
    fansCount = fansCount,
    followCount = followCount,
    workCount = workCount
)