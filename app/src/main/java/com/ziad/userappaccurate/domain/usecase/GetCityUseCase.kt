package com.ziad.userappaccurate.domain.usecase

import com.ziad.userappaccurate.domain.model.City
import com.ziad.userappaccurate.domain.repository.ICityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val repository: ICityRepository
) {
    operator fun invoke(): Flow<List<City>> = repository.getCities()
}