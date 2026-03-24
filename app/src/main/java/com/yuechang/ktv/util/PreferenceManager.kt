package com.yuechang.ktv.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户偏好设置管理
 */
@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    var token: String?
        get() = prefs.getString(KEY_TOKEN, null)
        set(value) = prefs.edit().putString(KEY_TOKEN, value).apply()
    
    var refreshToken: String?
        get() = prefs.getString(KEY_REFRESH_TOKEN, null)
        set(value) = prefs.edit().putString(KEY_REFRESH_TOKEN, value).apply()
    
    var userId: String?
        get() = prefs.getString(KEY_USER_ID, null)
        set(value) = prefs.edit().putString(KEY_USER_ID, value).apply()
    
    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()
    
    var earMonitorEnabled: Boolean
        get() = prefs.getBoolean(KEY_EAR_MONITOR, true)
        set(value) = prefs.edit().putBoolean(KEY_EAR_MONITOR, value).apply()
    
    var pitchCorrectionEnabled: Boolean
        get() = prefs.getBoolean(KEY_PITCH_CORRECTION, false)
        set(value) = prefs.edit().putBoolean(KEY_PITCH_CORRECTION, value).apply()
    
    fun clear() {
        prefs.edit().clear().apply()
    }
    
    companion object {
        private const val PREFS_NAME = "yuechang_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_EAR_MONITOR = "ear_monitor_enabled"
        private const val KEY_PITCH_CORRECTION = "pitch_correction_enabled"
    }
}