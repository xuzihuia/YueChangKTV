package com.yuechang.ktv.di

import android.content.Context
import androidx.room.Room
import com.yuechang.ktv.data.local.database.AppDatabase
import com.yuechang.ktv.data.local.dao.*
import com.yuechang.ktv.data.remote.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "yuechang_ktv.db"
        ).build()
    }
    
    @Provides
    fun provideSongDao(database: AppDatabase): SongDao = database.songDao()
    
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
    
    @Provides
    fun provideWorkDao(database: AppDatabase): WorkDao = database.workDao()
    
    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao = database.categoryDao()
}