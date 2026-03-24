package com.yuechang.ktv.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yuechang.ktv.databinding.ActivitySplashBinding
import com.yuechang.ktv.util.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 启动页
 */
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySplashBinding
    
    @Inject
    lateinit var preferenceManager: PreferenceManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        lifecycleScope.launch {
            delay(1500) // 显示启动页 1.5 秒
            
            val intent = if (preferenceManager.isLoggedIn) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            
            startActivity(intent)
            finish()
        }
    }
}