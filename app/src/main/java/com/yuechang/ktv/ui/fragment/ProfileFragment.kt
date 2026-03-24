package com.yuechang.ktv.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuechang.ktv.R
import com.yuechang.ktv.data.model.User
import com.yuechang.ktv.data.model.Work
import com.yuechang.ktv.ui.activity.LoginActivity
import com.yuechang.ktv.util.PreferenceManager

/**
 * 个人中心Fragment
 * 用户信息、我的作品、VIP入口
 */
class ProfileFragment : Fragment() {
    
    private lateinit var tvNickname: TextView
    private lateinit var tvVipStatus: TextView
    private lateinit var btnLogin: Button
    private lateinit var btnVip: Button
    private lateinit var rvWorks: RecyclerView
    
    private lateinit var preferenceManager: PreferenceManager
    private var isLoggedIn = false
    private val works = mutableListOf<Work>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        preferenceManager = PreferenceManager(requireContext())
        
        initViews(view)
        setupListeners()
        loadData()
    }
    
    private fun initViews(view: View) {
        tvNickname = view.findViewById(R.id.tv_nickname)
        tvVipStatus = view.findViewById(R.id.tv_vip_status)
        btnLogin = view.findViewById(R.id.btn_login)
        btnVip = view.findViewById(R.id.btn_vip)
        rvWorks = view.findViewById(R.id.rv_works)
        
        rvWorks.layoutManager = LinearLayoutManager(context)
    }
    
    private fun setupListeners() {
        btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        
        btnVip.setOnClickListener {
            // TODO: 打开VIP购买页
        }
    }
    
    private fun loadData() {
        isLoggedIn = preferenceManager.isLoggedIn
        
        if (!isLoggedIn) {
            btnLogin.visibility = View.VISIBLE
            tvNickname.text = "未登录"
            tvVipStatus.visibility = View.GONE
            return
        }
        
        btnLogin.visibility = View.GONE
        tvNickname.text = preferenceManager.userNickname ?: "用户"
        
        // Mock 作品数据
        works.addAll(listOf(
            Work("1", "user1", "song1", "晴天", "周杰伦", score = 85, scoreLevel = "A"),
            Work("2", "user1", "song2", "稻香", "周杰伦", score = 92, scoreLevel = "S"),
            Work("3", "user1", "song3", "起风了", "买辣椒也用券", score = 78, scoreLevel = "B")
        ))
        
        rvWorks.adapter = WorkAdapter(works) { work ->
            // 点击作品
        }
    }
    
    inner class WorkAdapter(
        private val works: List<Work>,
        private val onItemClick: (Work) -> Unit
    ) : RecyclerView.Adapter<WorkAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvSongName: TextView = view.findViewById(R.id.tv_song_name)
            val tvArtist: TextView = view.findViewById(R.id.tv_artist)
            val tvScore: TextView = view.findViewById(R.id.tv_score)
            val tvLevel: TextView = view.findViewById(R.id.tv_level)
            
            init {
                view.setOnClickListener { onItemClick(works[adapterPosition]) }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_work, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val work = works[position]
            holder.tvSongName.text = work.songName
            holder.tvArtist.text = work.artist
            holder.tvScore.text = "${work.score}分"
            holder.tvLevel.text = work.scoreLevel
        }
        
        override fun getItemCount() = works.size
    }
}