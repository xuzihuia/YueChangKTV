package com.yuechang.ktv.data.remote.api

import com.yuechang.ktv.data.remote.dto.*
import retrofit2.http.*

/**
 * 用户 API 接口
 */
interface UserApi {
    
    @POST("user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): ApiResponse<LoginResponse>
    
    @POST("user/login/sms")
    suspend fun loginBySms(
        @Body request: SmsLoginRequest
    ): ApiResponse<LoginResponse>
    
    @POST("user/logout")
    suspend fun logout(): ApiResponse<Unit>
    
    @GET("user/profile")
    suspend fun getProfile(): ApiResponse<UserDto>
    
    @PUT("user/profile")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ): ApiResponse<UserDto>
    
    @GET("user/vip/info")
    suspend fun getVipInfo(): ApiResponse<VipInfoDto>
    
    @POST("user/vip/purchase")
    suspend fun purchaseVip(
        @Body request: PurchaseVipRequest
    ): ApiResponse<VipPurchaseResultDto>
}