package com.ziad.userappaccurate.data.repository

import com.ziad.userappaccurate.data.local.dao.CityDao
import com.ziad.userappaccurate.data.mapper.toDomain
import com.ziad.userappaccurate.data.mapper.toEntity
import com.ziad.userappaccurate.data.remote.api.ApiService
import com.ziad.userappaccurate.domain.model.City
import com.ziad.userappaccurate.domain.repository.ICityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val cityDao: CityDao
) : ICityRepository {

    override fun getCities(): Flow<List<City>> =
        cityDao.getAllCities().map { it.map { entity -> entity.toDomain() } }

    override suspend fun syncCities(): Result<Unit> = runCatching {
        val cities = apiService.getCities()
        cityDao.upsertCities(cities.map { it.toEntity() })
    }
}