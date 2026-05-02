package com.ziad.userappaccurate.domain.repository

import com.ziad.userappaccurate.domain.model.City
import kotlinx.coroutines.flow.Flow

interface ICityRepository {
    fun getCities(): Flow<List<City>>
    suspend fun syncCities(): Result<Unit>
}