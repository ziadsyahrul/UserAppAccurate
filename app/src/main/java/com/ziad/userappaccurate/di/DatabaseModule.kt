package com.ziad.userappaccurate.di

import android.content.Context
import androidx.room.Room
import com.ziad.userappaccurate.data.local.AppDatabase
import com.ziad.userappaccurate.data.local.dao.CityDao
import com.ziad.userappaccurate.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "accurate_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
    @Provides fun provideCityDao(db: AppDatabase): CityDao = db.cityDao()
}