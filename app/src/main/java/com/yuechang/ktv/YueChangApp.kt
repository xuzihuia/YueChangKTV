package com.yuechang.ktv

import android.app.Application
import android.content.Context

class YueChangApp : Application() {
    
    companion object {
        lateinit var instance: YueChangApp
            private set
        
        fun getContext(): Context = instance.applicationContext
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}