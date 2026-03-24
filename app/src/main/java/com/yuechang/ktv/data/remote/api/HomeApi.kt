package com.yuechang.ktv.data.remote.api

import com.yuechang.ktv.data.remote.dto.*
import retrofit2.http.*

/**
 * 首页 API 接口
 */
interface HomeApi {
    
    @GET("home/banners")
    suspend fun getBanners(): ApiResponse<List<BannerDto>>
    
    @GET("home/recommend")
    suspend fun getRecommendSongs(
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
    
    @GET("home/categories")
    suspend fun getCategories(): ApiResponse<List<CategoryDto>>
    
    @GET("home/playlists")
    suspend fun getPlaylists(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): ApiResponse<List<PlaylistDto>>
}