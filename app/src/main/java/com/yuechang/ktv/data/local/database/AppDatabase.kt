package com.yuechang.ktv.data.local.database

import androidx.room.*
import com.yuechang.ktv.data.local.dao.*
import com.yuechang.ktv.data.local.entity.*

@Database(
    entities = [
        SongEntity::class,
        UserEntity::class,
        WorkEntity::class,
        CategoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun userDao(): UserDao
    abstract fun workDao(): WorkDao
    abstract fun categoryDao(): CategoryDao
}