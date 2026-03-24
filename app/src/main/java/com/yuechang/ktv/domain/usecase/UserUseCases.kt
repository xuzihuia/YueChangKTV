package com.yuechang.ktv.domain.usecase

import com.yuechang.ktv.data.repository.UserRepository
import com.yuechang.ktv.data.local.entity.UserEntity
import com.yuechang.ktv.domain.model.User
import com.yuechang.ktv.domain.model.VipType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 获取当前用户
 */
class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: String): Flow<User?> {
        return userRepository.getCurrentUser(userId).map { entity ->
            entity?.toDomainModel()
        }
    }
}

/**
 * 登录
 */
class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phone: String, password: String): Result<User> {
        return userRepository.login(phone, password).map { it.toDomainModel() }
    }
}

/**
 * 短信登录
 */
class SmsLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phone: String, code: String): Result<User> {
        return userRepository.loginBySms(phone, code).map { it.toDomainModel() }
    }
}

/**
 * 登出
 */
class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return userRepository.logout()
    }
}

/**
 * 刷新用户信息
 */
class RefreshUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<User> {
        return userRepository.refreshProfile().map { it.toDomainModel() }
    }
}

/**
 * 检查 VIP 状态
 */
class CheckVipStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Boolean {
        // 实际应该从本地或远程获取用户信息判断
        return true
    }
}

// Entity 转 Domain Model
private fun UserEntity.toDomainModel() = User(
    userId = userId,
    nickname = nickname,
    avatar = avatar,
    vipLevel = vipLevel,
    vipExpireTime = vipExpireTime,
    coins = coins,
    beans = beans
)