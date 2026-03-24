package com.yuechang.ktv.data.remote.api

import com.yuechang.ktv.data.remote.dto.*
import retrofit2.http.*

/**
 * 作品 API 接口
 */
interface WorkApi {
    
    @GET("works/user/{userId}")
    suspend fun getUserWorks(
        @Path("userId") userId: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<WorkDto>>
    
    @GET("works/hot")
    suspend fun getHotWorks(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<WorkDto>>
    
    @GET("works/{workId}")
    suspend fun getWorkDetail(
        @Path("workId") workId: String
    ): ApiResponse<WorkDetailDto>
    
    @POST("works")
    suspend fun uploadWork(
        @Body request: UploadWorkRequest
    ): ApiResponse<WorkDto>
    
    @DELETE("works/{workId}")
    suspend fun deleteWork(
        @Path("workId") workId: String
    ): ApiResponse<Unit>
    
    @POST("works/{workId}/like")
    suspend fun likeWork(
        @Path("workId") workId: String
    ): ApiResponse<LikeResultDto>
    
    @POST("works/{workId}/share")
    suspend fun shareWork(
        @Path("workId") workId: String
    ): ApiResponse<ShareResultDto>
}