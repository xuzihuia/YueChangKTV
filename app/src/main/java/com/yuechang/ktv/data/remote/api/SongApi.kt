package com.yuechang.ktv.data.remote.api

import com.yuechang.ktv.data.remote.dto.*
import retrofit2.http.*

/**
 * 歌曲 API 接口
 */
interface SongApi {
    
    @GET("songs/hot")
    suspend fun getHotSongs(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
    
    @GET("songs/new")
    suspend fun getNewSongs(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
    
    @GET("songs/category/{categoryId}")
    suspend fun getSongsByCategory(
        @Path("categoryId") categoryId: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
    
    @GET("songs/search")
    suspend fun searchSongs(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
    
    @GET("songs/{songId}")
    suspend fun getSongDetail(
        @Path("songId") songId: String
    ): ApiResponse<SongDetailDto>
    
    @GET("songs/{songId}/lyrics")
    suspend fun getLyrics(
        @Path("songId") songId: String
    ): ApiResponse<LyricsDto>
    
    @GET("songs/free")
    suspend fun getFreeSongs(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
}