package com.yuechang.ktv.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yuechang.ktv.R
import com.yuechang.ktv.ui.fragment.*

/**
 * 主页
 * 底部导航：首页、分类、搜索、我的
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var bottomNav: BottomNavigationView
    
    private var currentFragment: Fragment? = null
    private var lastBackPressed = 0L
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        setupBottomNav()
        
        // 默认显示首页
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }
    
    private fun initViews() {
        fragmentContainer = findViewById(R.id.fragment_container)
        bottomNav = findViewById(R.id.bottom_nav)
    }
    
    private fun setupBottomNav() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_category -> {
                    loadFragment(CategoryFragment())
                    true
                }
                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }
    
    private fun loadFragment(fragment: Fragment) {
        if (currentFragment != null && currentFragment!!::class == fragment::class) {
            return
        }
        
        currentFragment = fragment
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss()
    }
    
    /**
     * 跳转到K歌页面
     */
    fun openKaraokeActivity(songId: String, songName: String, artist: String) {
        val intent = Intent(this, KaraokeActivity::class.java).apply {
            putExtra("songId", songId)
            putExtra("songName", songName)
            putExtra("artist", artist)
        }
        startActivity(intent)
    }
    
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val now = System.currentTimeMillis()
            if (now - lastBackPressed < 2000) {
                finish()
            } else {
                lastBackPressed = now
                // 提示再按一次退出
                findViewById<TextView>(R.id.tv_hint)?.let {
                    it.visibility = View.VISIBLE
                    it.postDelayed({ it.visibility = View.GONE }, 2000)
                }
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}