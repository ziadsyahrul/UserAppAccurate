package com.ziad.userappaccurate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ziad.userappaccurate.data.local.dao.CityDao
import com.ziad.userappaccurate.data.local.dao.UserDao
import com.ziad.userappaccurate.data.local.entity.CityEntity
import com.ziad.userappaccurate.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, CityEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cityDao(): CityDao
}