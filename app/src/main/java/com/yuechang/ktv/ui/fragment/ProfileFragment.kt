package com.yuechang.ktv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuechang.ktv.databinding.FragmentProfileBinding
import com.yuechang.ktv.ui.adapter.WorkAdapter
import com.yuechang.ktv.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 我的 Fragment
 * 个人中心、我的作品、收藏
 */
@AndroidEntryPoint
class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: UserViewModel by viewModels()
    private lateinit var workAdapter: WorkAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        workAdapter = WorkAdapter { work ->
            // 点击作品
        }
        
        binding.rvMyWorks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = workAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.tvNickname.text = it.nickname
                binding.tvVipStatus.text = if (it.isVip) "VIP会员" else "普通用户"
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            // 跳转登录
        }
        
        binding.btnVip.setOnClickListener {
            // 购买 VIP
        }
        
        binding.btnSettings.setOnClickListener {
            // 设置
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}