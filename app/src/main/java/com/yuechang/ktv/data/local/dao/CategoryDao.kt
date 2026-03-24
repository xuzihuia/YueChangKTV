package com.yuechang.ktv.data.local.dao

import androidx.room.*
import com.yuechang.ktv.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    
    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    suspend fun getCategoryById(categoryId: String): CategoryEntity?
    
    @Query("SELECT * FROM categories WHERE parentId IS NULL AND isActive = 1 ORDER BY sortOrder")
    fun getRootCategories(): Flow<List<CategoryEntity>>
    
    @Query("SELECT * FROM categories WHERE parentId = :parentId AND isActive = 1 ORDER BY sortOrder")
    fun getSubCategories(parentId: String): Flow<List<CategoryEntity>>
    
    @Query("SELECT * FROM categories WHERE isActive = 1 ORDER BY sortOrder")
    fun getAllCategories(): Flow<List<CategoryEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)
    
    @Update
    suspend fun updateCategory(category: CategoryEntity)
    
    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
}