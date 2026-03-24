package com.yuechang.ktv.di

import com.yuechang.ktv.BuildConfig
import com.yuechang.ktv.data.mock.MockInterceptor
import com.yuechang.ktv.data.remote.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    private const val BASE_URL = "https://api.yuechang.ktv/v1/"
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                // 添加 Mock 拦截器（仅调试模式）
                if (BuildConfig.DEBUG) {
                    addInterceptor(MockInterceptor())
                }
                // 日志拦截器
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideSongApi(retrofit: Retrofit): SongApi = retrofit.create(SongApi::class.java)
    
    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
    
    @Provides
    @Singleton
    fun provideWorkApi(retrofit: Retrofit): WorkApi = retrofit.create(WorkApi::class.java)
    
    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)
}