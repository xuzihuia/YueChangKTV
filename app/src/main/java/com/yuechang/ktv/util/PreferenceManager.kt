package com.yuechang.ktv.util

import android.content.Context
import android.content.SharedPreferences

/**
 * 偏好设置管理
 */
class PreferenceManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    var token: String?
        get() = prefs.getString(KEY_TOKEN, null)
        set(value) = prefs.edit().putString(KEY_TOKEN, value).apply()
    
    var refreshToken: String?
        get() = prefs.getString(KEY_REFRESH_TOKEN, null)
        set(value) = prefs.edit().putString(KEY_REFRESH_TOKEN, value).apply()
    
    var userId: String?
        get() = prefs.getString(KEY_USER_ID, null)
        set(value) = prefs.edit().putString(KEY_USER_ID, value).apply()
    
    var userNickname: String?
        get() = prefs.getString(KEY_USER_NICKNAME, null)
        set(value) = prefs.edit().putString(KEY_USER_NICKNAME, value).apply()
    
    var userAvatar: String?
        get() = prefs.getString(KEY_USER_AVATAR, null)
        set(value) = prefs.edit().putString(KEY_USER_AVATAR, value).apply()
    
    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()
    
    var isVip: Boolean
        get() = prefs.getBoolean(KEY_IS_VIP, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_VIP, value).apply()
    
    var vipExpireTime: Long
        get() = prefs.getLong(KEY_VIP_EXPIRE_TIME, 0)
        set(value) = prefs.edit().putLong(KEY_VIP_EXPIRE_TIME, value).apply()
    
    fun clear() {
        prefs.edit().clear().apply()
    }
    
    fun clearUserInfo() {
        prefs.edit().apply {
            remove(KEY_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_USER_ID)
            remove(KEY_USER_NICKNAME)
            remove(KEY_USER_AVATAR)
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_IS_VIP)
            remove(KEY_VIP_EXPIRE_TIME)
        }.apply()
    }
    
    companion object {
        private const val PREFS_NAME = "yuechang_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NICKNAME = "user_nickname"
        private const val KEY_USER_AVATAR = "user_avatar"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_IS_VIP = "is_vip"
        private const val KEY_VIP_EXPIRE_TIME = "vip_expire_time"
    }
}