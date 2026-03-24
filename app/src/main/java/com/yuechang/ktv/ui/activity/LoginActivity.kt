package com.yuechang.ktv.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.yuechang.ktv.databinding.ActivityLoginBinding
import com.yuechang.ktv.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 登录界面
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(phone, password)
        }
        
        binding.btnSmsLogin.setOnClickListener {
            // 短信登录
        }
        
        binding.btnWechatLogin.setOnClickListener {
            // 微信登录
        }
    }
}