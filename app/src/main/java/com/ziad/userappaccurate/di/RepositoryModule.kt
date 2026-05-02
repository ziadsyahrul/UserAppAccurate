package com.ziad.userappaccurate.di

import com.ziad.userappaccurate.data.repository.CityRepositoryImpl
import com.ziad.userappaccurate.data.repository.UserRepositoryImpl
import com.ziad.userappaccurate.domain.repository.ICityRepository
import com.ziad.userappaccurate.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): IUserRepository

    @Binds
    @Singleton
    abstract fun bindCityRepository(impl: CityRepositoryImpl): ICityRepository
}