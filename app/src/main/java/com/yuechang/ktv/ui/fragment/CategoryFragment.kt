package com.yuechang.ktv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuechang.ktv.R
import com.yuechang.ktv.data.model.Category

/**
 * 分类Fragment
 * 热门/新歌/经典/华语/欧美/日韩/儿童/长辈/免费/综艺
 */
class CategoryFragment : Fragment() {
    
    private lateinit var rvCategories: RecyclerView
    
    private val categories = mutableListOf<Category>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        rvCategories = view.findViewById(R.id.rv_categories)
        rvCategories.layoutManager = GridLayoutManager(context, 2)
        
        loadData()
    }
    
    private fun loadData() {
        // Mock 分类数据
        categories.addAll(listOf(
            Category("hot", "热门推荐", sortOrder = 1),
            Category("new", "新歌速递", sortOrder = 2),
            Category("classic", "经典老歌", sortOrder = 3),
            Category("chinese", "华语金曲", sortOrder = 4),
            Category("western", "欧美流行", sortOrder = 5),
            Category("korean", "日韩音乐", sortOrder = 6),
            Category("children", "儿童专区", sortOrder = 7),
            Category("elder", "长辈专区", sortOrder = 8),
            Category("free", "免费专区", sortOrder = 9),
            Category("variety", "综艺歌曲", sortOrder = 10)
        ))
        
        rvCategories.adapter = CategoryAdapter(categories) { category ->
            openCategory(category)
        }
    }
    
    private fun openCategory(category: Category) {
        // TODO: 打开分类详情页
    }
    
    inner class CategoryAdapter(
        private val categories: List<Category>,
        private val onItemClick: (Category) -> Unit
    ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tv_category_name)
            val tvCount: TextView = view.findViewById(R.id.tv_song_count)
            
            init {
                view.setOnClickListener {
                    onItemClick(categories[adapterPosition])
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val category = categories[position]
            holder.tvName.text = category.name
            holder.tvCount.text = "${category.songCount}首"
        }
        
        override fun getItemCount() = categories.size
    }
}