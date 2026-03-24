package com.yuechang.ktv

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class YueChangApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // 初始化 Timber 日志
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        Timber.d("YueChangKTV App initialized")
    }
}