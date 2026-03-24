package com.yuechang.ktv.data.remote.api

import com.yuechang.ktv.data.remote.dto.*
import retrofit2.http.*

/**
 * 首页相关接口
 */
interface HomeApi {
    
    @GET("home/banners")
    suspend fun getBanners(): ApiResponse<List<BannerDto>>
    
    @GET("home/recommend")
    suspend fun getRecommendSongs(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
    
    @GET("home/hot")
    suspend fun getHotSongs(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
    
    @GET("home/new")
    suspend fun getNewSongs(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
    
    @GET("home/free")
    suspend fun getFreeSongs(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
}

/**
 * 歌曲相关接口
 */
interface SongApi {
    
    @GET("songs/{id}")
    suspend fun getSongDetail(@Path("id") songId: String): ApiResponse<SongDetailDto>
    
    @GET("songs/category/{categoryId}")
    suspend fun getSongsByCategory(
        @Path("categoryId") categoryId: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
    
    @GET("songs/search")
    suspend fun searchSongs(
        @Query("keyword") keyword: String,
        @Query("type") type: String = "all",  // all, song, artist
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<SearchResultDto>
    
    @GET("songs/{id}/lyrics")
    suspend fun getLyrics(@Path("id") songId: String): ApiResponse<LyricsDto>
    
    @GET("songs/{id}/pitch")
    suspend fun getPitchData(@Path("id") songId: String): ApiResponse<List<PitchPointDto>>
    
    @POST("songs/{id}/play")
    suspend fun recordPlay(@Path("id") songId: String): ApiResponse<Unit>
    
    @POST("songs/{id}/favorite")
    suspend fun toggleFavorite(@Path("id") songId: String): ApiResponse<FavoriteResultDto>
}

/**
 * 分类相关接口
 */
interface CategoryApi {
    
    @GET("categories")
    suspend fun getCategories(): ApiResponse<List<CategoryDto>>
    
    @GET("categories/{id}")
    suspend fun getCategoryDetail(@Path("id") categoryId: String): ApiResponse<CategoryDto>
    
    @GET("categories/{id}/songs")
    suspend fun getCategorySongs(
        @Path("id") categoryId: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
}

/**
 * 用户相关接口
 */
interface UserApi {
    
    @POST("user/login/phone")
    suspend fun loginByPhone(@Body request: PhoneLoginRequest): ApiResponse<LoginResponse>
    
    @POST("user/login/password")
    suspend fun loginByPassword(@Body request: PasswordLoginRequest): ApiResponse<LoginResponse>
    
    @POST("user/login/sms")
    suspend fun loginBySms(@Body request: SmsLoginRequest): ApiResponse<LoginResponse>
    
    @POST("user/logout")
    suspend fun logout(): ApiResponse<Unit>
    
    @GET("user/profile")
    suspend fun getProfile(): ApiResponse<UserDto>
    
    @PUT("user/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): ApiResponse<UserDto>
    
    @GET("user/vip/info")
    suspend fun getVipInfo(): ApiResponse<VipInfoDto>
}

/**
 * 作品相关接口
 */
interface WorkApi {
    
    @GET("works/user/{userId}")
    suspend fun getUserWorks(
        @Path("userId") userId: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<WorkDto>>
    
    @GET("works/{id}")
    suspend fun getWorkDetail(@Path("id") workId: String): ApiResponse<WorkDetailDto>
    
    @POST("works")
    suspend fun uploadWork(@Body request: UploadWorkRequest): ApiResponse<WorkDto>
    
    @DELETE("works/{id}")
    suspend fun deleteWork(@Path("id") workId: String): ApiResponse<Unit>
    
    @POST("works/{id}/like")
    suspend fun likeWork(@Path("id") workId: String): ApiResponse<LikeResultDto>
    
    @POST("works/{id}/share")
    suspend fun shareWork(@Path("id") workId: String): ApiResponse<ShareResultDto>
}

/**
 * 歌手相关接口
 */
interface ArtistApi {
    
    @GET("artists")
    suspend fun getArtists(
        @Query("type") type: String? = null,  // hot, new
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<ArtistDto>>
    
    @GET("artists/{id}")
    suspend fun getArtistDetail(@Path("id") artistId: String): ApiResponse<ArtistDetailDto>
    
    @GET("artists/{id}/songs")
    suspend fun getArtistSongs(
        @Path("id") artistId: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<List<SongDto>>
}