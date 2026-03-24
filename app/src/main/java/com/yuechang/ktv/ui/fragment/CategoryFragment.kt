package com.yuechang.ktv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yuechang.ktv.databinding.FragmentCategoryBinding
import com.yuechang.ktv.ui.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * 分类 Fragment
 * 对标唱享K歌分类页：热门、新歌、经典、华语、欧美、日韩、少儿、长辈专区
 */
@AndroidEntryPoint
class CategoryFragment : Fragment() {
    
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var categoryAdapter: CategoryAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }
    
    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            // 点击分类，显示该分类歌曲列表
        }
        
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoryAdapter
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}