package com.yuechang.ktv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuechang.ktv.databinding.FragmentSearchBinding
import com.yuechang.ktv.ui.adapter.SongAdapter
import com.yuechang.ktv.ui.adapter.SearchHistoryAdapter
import com.yuechang.ktv.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 搜索 Fragment
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {
    
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var songAdapter: SongAdapter
    private lateinit var historyAdapter: SearchHistoryAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupObservers()
        setupSearchView()
    }
    
    private fun setupRecyclerViews() {
        songAdapter = SongAdapter { song ->
            // 点击歌曲
        }
        
        historyAdapter = SearchHistoryAdapter { query ->
            viewModel.searchWithQuery(query)
        }
        
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = songAdapter
        }
        
        binding.rvSearchHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }
    }
    
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchWithQuery(it) }
                return true
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.updateQuery(newText ?: "")
                return true
            }
        })
    }
    
    private fun setupObservers() {
        viewModel.searchResults.observe(viewLifecycleOwner) { songs ->
            songAdapter.submitList(songs)
            binding.rvSearchResults.visibility = if (songs.isNotEmpty()) View.VISIBLE else View.GONE
            binding.rvSearchHistory.visibility = if (songs.isEmpty()) View.VISIBLE else View.GONE
        }
        
        viewModel.searchHistory.observe(viewLifecycleOwner) { history ->
            historyAdapter.submitList(history)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}