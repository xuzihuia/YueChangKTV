package com.yuechang.ktv.di

import android.content.Context
import com.yuechang.ktv.service.player.KaraokePlayer
import com.yuechang.ktv.service.recorder.KaraokeRecorder
import com.yuechang.ktv.service.audio.ScoreEngine
import com.yuechang.ktv.service.audio.LyricsParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    
    @Provides
    @Singleton
    fun provideKaraokePlayer(@ApplicationContext context: Context): KaraokePlayer {
        return KaraokePlayer(context).apply { initialize() }
    }
    
    @Provides
    @Singleton
    fun provideKaraokeRecorder(@ApplicationContext context: Context): KaraokeRecorder {
        return KaraokeRecorder(context)
    }
    
    @Provides
    @Singleton
    fun provideScoreEngine(): ScoreEngine = ScoreEngine()
    
    @Provides
    @Singleton
    fun provideLyricsParser(): LyricsParser = LyricsParser()
}