package com.ziad.userappaccurate.domain.usecase

import com.ziad.userappaccurate.domain.repository.ICityRepository
import com.ziad.userappaccurate.domain.repository.IUserRepository
import javax.inject.Inject

class SyncUsersUseCase @Inject constructor(
    private val userRepository: IUserRepository,
    private val cityRepository: ICityRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        cityRepository.syncCities()
        return userRepository.syncUsers()
    }
}