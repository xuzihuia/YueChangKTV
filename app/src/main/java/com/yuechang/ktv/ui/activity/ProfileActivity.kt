package com.yuechang.ktv.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.yuechang.ktv.databinding.ActivityProfileBinding
import com.yuechang.ktv.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 个人中心界面
 */
@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: UserViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupObservers()
    }
    
    private fun setupObservers() {
        viewModel.currentUser.observe(this) { user ->
            user?.let {
                binding.tvNickname.text = it.nickname
                binding.tvVipStatus.text = if (it.isVip) "VIP会员" else "普通用户"
            }
        }
    }
}