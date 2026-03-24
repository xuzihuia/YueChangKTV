package com.yuechang.ktv.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yuechang.ktv.R
import com.yuechang.ktv.util.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 启动页
 * 品牌展示、初始化检查
 */
class SplashActivity : AppCompatActivity() {
    
    private lateinit var ivLogo: ImageView
    private lateinit var tvVersion: TextView
    private lateinit var progressView: View
    
    private val preferenceManager by lazy { PreferenceManager(this) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        initViews()
        startAnimation()
    }
    
    private fun initViews() {
        ivLogo = findViewById(R.id.iv_logo)
        tvVersion = findViewById(R.id.tv_version)
        progressView = findViewById(R.id.progress_bar)
        
        // 显示版本号
        try {
            val versionName = packageManager.getPackageInfo(packageName, 0).versionName
            tvVersion.text = "v$versionName"
        } catch (e: Exception) {
            tvVersion.text = "v1.0.0"
        }
    }
    
    private fun startAnimation() {
        lifecycleScope.launch {
            // 显示启动动画 2秒
            delay(2000)
            
            // 检查登录状态
            val intent = if (preferenceManager.isLoggedIn) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            
            startActivity(intent)
            finish()
            
            // 淡出动画
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}