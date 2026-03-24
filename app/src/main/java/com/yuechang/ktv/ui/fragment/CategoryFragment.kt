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

class CategoryFragment : Fragment() {
    
    private lateinit var rvCategories: RecyclerView
    
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
        
        loadCategories()
    }
    
    private fun loadCategories() {
        val categories = listOf(
            Category("hot", "热门推荐"),
            Category("new", "新歌速递"),
            Category("classic", "经典老歌"),
            Category("chinese", "华语金曲"),
            Category("western", "欧美流行"),
            Category("korean", "日韩音乐"),
            Category("children", "儿童专区"),
            Category("elder", "长辈专区"),
            Category("free", "免费专区"),
            Category("variety", "综艺歌曲")
        )
        
        rvCategories.adapter = CategoryAdapter(categories)
    }
    
    inner class CategoryAdapter(
        private val categories: List<Category>
    ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(R.id.tv_category_name)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.name.text = categories[position].name
        }
        
        override fun getItemCount() = categories.size
    }
}