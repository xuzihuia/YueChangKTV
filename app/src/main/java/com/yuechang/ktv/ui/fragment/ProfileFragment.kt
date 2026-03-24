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
import com.yuechang.ktv.data.model.Work
import com.yuechang.ktv.ui.activity.LoginActivity

class ProfileFragment : Fragment() {
    
    private lateinit var tvNickname: TextView
    private lateinit var tvVipStatus: TextView
    private lateinit var btnLogin: Button
    private lateinit var btnVip: Button
    private lateinit var rvWorks: RecyclerView
    
    private var isLoggedIn = false
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        tvNickname = view.findViewById(R.id.tv_nickname)
        tvVipStatus = view.findViewById(R.id.tv_vip_status)
        btnLogin = view.findViewById(R.id.btn_login)
        btnVip = view.findViewById(R.id.btn_vip)
        rvWorks = view.findViewById(R.id.rv_works)
        
        setupViews()
        loadData()
    }
    
    private fun setupViews() {
        btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        
        btnVip.setOnClickListener {
            // TODO: VIP 购买
        }
        
        rvWorks.layoutManager = LinearLayoutManager(context)
    }
    
    private fun loadData() {
        if (!isLoggedIn) {
            btnLogin.visibility = View.VISIBLE
            tvNickname.text = "未登录"
            tvVipStatus.visibility = View.GONE
            return
        }
        
        btnLogin.visibility = View.GONE
        tvNickname.text = "用户昵称"
        tvVipStatus.visibility = View.VISIBLE
        
        // Mock 作品列表
        val works = listOf(
            Work("1", "晴天", 85),
            Work("2", "稻香", 92),
            Work("3", "起风了", 78)
        )
        rvWorks.adapter = WorkAdapter(works)
    }
    
    inner class WorkAdapter(
        private val works: List<Work>
    ) : RecyclerView.Adapter<WorkAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val songName: TextView = view.findViewById(R.id.tv_song_name)
            val score: TextView = view.findViewById(R.id.tv_score)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_work, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val work = works[position]
            holder.songName.text = work.songName
            holder.score.text = "${work.score}分"
        }
        
        override fun getItemCount() = works.size
    }
}